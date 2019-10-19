package vavkhan.com.sanagostar.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import vavkhan.com.sanagostar.Net.Status;
import vavkhan.com.sanagostar.models.SendAddressModel;
import vavkhan.com.sanagostar.R;
import vavkhan.com.sanagostar.ViewModels.RegisterViewModel;
import vavkhan.com.sanagostar.databinding.RegisterActivityBinding;

public class RegisterActivity extends BaseActivity<RegisterActivityBinding> implements LifecycleOwner {
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    RegisterViewModel registerViewModel;
    @Inject
    Context appContext;
    final static int MAP_FRAGMENT = 0,REGISTER_FRAGMENT = 1;
    int currentFragment = REGISTER_FRAGMENT;
    private static final int REQUEST_MULTIPLE_PERMISSION = 117;
    @Override
    public int getLayoutRes() {
        return R.layout.register_activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(dataBinding.navView.getId(), DrawerFragment.newInstance(dataBinding.drawerLayout)).commit();
        dataBinding.toggleDrawer.setOnClickListener(view ->{
            dataBinding.drawerLayout.openDrawer(Gravity.RIGHT);
        });
        registerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel.class);
        if (Build.VERSION.SDK_INT >= 23) {

            List<String> permissions = new ArrayList<String>();
            if (ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissions.size() > 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), REQUEST_MULTIPLE_PERMISSION);
            }
        }
        getSupportFragmentManager().beginTransaction().
                replace(dataBinding.fragmentContainer.getId(), RegisterFragment.newInstance()).commit();
        currentFragment = REGISTER_FRAGMENT;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        if (lifecycleRegistry == null)
            lifecycleRegistry = new LifecycleRegistry(this);
        return lifecycleRegistry;
    }

    public void changeToMap(){
        getSupportFragmentManager().beginTransaction().
                replace(dataBinding.fragmentContainer.getId(), MapFragment.newInstance()).commit();
        currentFragment = MAP_FRAGMENT;
    }
    public void changeToRegister(){
        getSupportFragmentManager().beginTransaction().
                replace(dataBinding.fragmentContainer.getId(), RegisterFragment.newInstance()).commit();
        currentFragment = REGISTER_FRAGMENT;
    }

    public void setRegisterModl(SendAddressModel model){
        registerViewModel.setSendAddressModel(model);
    }

    public SendAddressModel getAddressModel(){
        return registerViewModel.getSendAddressModel();
    }

    public void sendDataToServer(){
        registerViewModel.sendData().observe(this,response->{
            if (response.status== Status.ERROR){
                toast(appContext,response.message);
            }else{
                toast(appContext,"ثبت انجام شد.");
                goToList();
            }
        });
    }

    private void goToList() {
        Intent listIntent = new Intent(this,ActivityList.class);
        listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(listIntent);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == MAP_FRAGMENT){
            changeToRegister();
        }else{
            super.onBackPressed();
        }

    }
}
