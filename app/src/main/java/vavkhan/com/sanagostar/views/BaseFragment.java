package vavkhan.com.sanagostar.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;
import vavkhan.com.sanagostar.R;

public abstract class BaseFragment<DB extends ViewDataBinding> extends Fragment
        implements LifecycleOwner {
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    Dialog progressDialog;
    public DB dataBinding;
    public static BaseActivity activity;

    @Inject
    Context appContext;
    @LayoutRes
    public abstract int getLayoutRes();
    /*public CatLoadingView mView ;*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        activity = (BaseActivity)getActivity();
        /* mView = new CatLoadingView();*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);

        return dataBinding.getRoot();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
    public void showProgress(){
        if (isAdded()) {
            if (progressDialog == null) {
                progressDialog = new Dialog(getActivity());
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setContentView(R.layout.progress_view_dialog);
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }
    public void hideProgress(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        hideProgress();
        super.onPause();
    }

    @Override
    public void onDetach() {
        //PosApplication.get(appContext).cancelAllRequest();
        super.onDetach();
    }
}
