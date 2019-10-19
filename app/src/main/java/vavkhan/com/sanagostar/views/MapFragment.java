package vavkhan.com.sanagostar.views;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import vavkhan.com.sanagostar.R;
import vavkhan.com.sanagostar.databinding.FragmentMapBinding;
import vavkhan.com.sanagostar.models.SendAddressModel;

public class MapFragment extends BaseFragment<FragmentMapBinding> {
    @Inject
    Context appContext;
    DrawerLayout drawerLayout;
    Location mLastKnownLocation;
    private static final int REQUEST_ENABLE_GPS = 917;
    MarkerOptions myMarker;
    GoogleMap gmp;
    private FusedLocationProviderClient fusedLocationClient;
    Dialog alertdialog;
    boolean isHandyLocation = false;

    SendAddressModel model;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_map;
    }

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        checkGPS();

        dataBinding.registerBtn.setOnClickListener(view -> {
            if (isAdded())
                sendData();
        });
        return dataBinding.getRoot();
    }

    private void sendData() {
        if (model == null){
            model = ((RegisterActivity)activity).getAddressModel();
        }
        model.setLatitude(String.valueOf(mLastKnownLocation.getLatitude()));
        model.setLongitude(String.valueOf(mLastKnownLocation.getLongitude()));
        model.setRegion(1);
        ((RegisterActivity)activity).setRegisterModl(model);
        ((RegisterActivity)activity).sendDataToServer();
    }

    private void checkGPS() {
        if (Build.VERSION.SDK_INT > 22) {
            final LocationManager manager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
            if (canToggleGPS()) {
                turnGPSOn(activity);
            } else {
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else {
                    showMarker();
                }

            }
        } else {
            showMarker();
        }
    }

    private void buildAlertMessageNoGps() {
        alertdialog = new Dialog(activity);
        alertdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertdialog.setContentView(R.layout.alert_dialog);
        alertdialog.setCancelable(false);

        TextView messageTxt = alertdialog.findViewById(R.id.message_txt);
        messageTxt.setText(appContext.getResources().getString(R.string.turnon_gps_msg));

        AppCompatButton okBtn = alertdialog.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(v -> {
            Toast.makeText(appContext, appContext.getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS);
            alertdialog.dismiss();
        });

        AppCompatButton cancelBtn = alertdialog.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(v -> {
            Toast.makeText(appContext, appContext.getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
            alertdialog.dismiss();

        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertdialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertdialog.show();
        alertdialog.getWindow().setAttributes(lp);
    }

    /**
     * Check If App Can Toggle GPS To on
     *
     * @return boolean for check can toggle gps automatically
     */
    public boolean canToggleGPS() {
        PackageManager pacman = activity.getPackageManager();
        PackageInfo pacInfo;

        try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
        } catch (PackageManager.NameNotFoundException e) {
            return false; //package not found
        }

        if (pacInfo != null) {
            for (ActivityInfo actInfo : pacInfo.receivers) {
                //test if recevier is exported. if so, we can toggle GPS.
                if (actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Turning The GPS On
     *
     * @param activity Activity used this method
     */
    public void turnGPSOn(Activity activity) {
        String provider = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            activity.sendBroadcast(poke);
        }
    }

    private void showMarker() {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        Objects.requireNonNull(mapFragment).getMapAsync(googleMap -> {
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                activity.toast(appContext, "You must set the location permission at first.");
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(18));

            googleMap.setOnMyLocationChangeListener(location -> {
                if (!isHandyLocation) {
                    if (mLastKnownLocation != null) {
                        if (mLastKnownLocation != location) {
                            mLastKnownLocation = location;
                        }
                    } else {
                        mLastKnownLocation = location;
                    }


                    double latitude = mLastKnownLocation.getLatitude();
                    double longitude = mLastKnownLocation.getLongitude();
                    LatLng myLoc = new LatLng(latitude, longitude);
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(myLoc).title("My Location"));

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));

                }
            });
            googleMap.setOnMapLongClickListener(latLng -> {
                isHandyLocation = true;
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mLastKnownLocation.setLatitude(latLng.latitude);
                mLastKnownLocation.setLongitude(latLng.longitude);
            });
        });
    }

}
