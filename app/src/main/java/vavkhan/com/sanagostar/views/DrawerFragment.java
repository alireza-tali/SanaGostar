package vavkhan.com.sanagostar.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import vavkhan.com.sanagostar.R;
import vavkhan.com.sanagostar.ViewModels.ListViewModel;
import vavkhan.com.sanagostar.databinding.FragmentDrawerBinding;

public class DrawerFragment extends BaseFragment<FragmentDrawerBinding>{
    @Inject
    Context appContext;
    DrawerLayout drawerLayout;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_drawer;
    }

    public static DrawerFragment newInstance(DrawerLayout drawerLayout) {
        Bundle args = new Bundle();
        args.putInt("drawerLayout",drawerLayout.getId());
        DrawerFragment fragment = new DrawerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        int drawerId = getArguments().getInt("drawerLayout");
        drawerLayout = activity.findViewById(drawerId);

        dataBinding.listBtn.setOnClickListener(view->{
            if (!(activity instanceof ActivityList)) {
                Intent listIntent = new Intent(activity,ActivityList.class);
                listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(listIntent);
            }
        });

        dataBinding.registerBtn.setOnClickListener(view->{
            if (!(activity instanceof RegisterActivity)) {
                Intent registerIntent = new Intent(activity,RegisterActivity.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(registerIntent);
            }
        });

        return dataBinding.getRoot();
    }

    @Override
    public void onPause() {
        try {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }catch (Exception ex){

        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        try {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        super.onDestroy();
    }
}
