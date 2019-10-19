package vavkhan.com.sanagostar.views;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import vavkhan.com.sanagostar.R;

/*import com.appsun.pos.Libraries.catloadinglibrary.CatLoadingView;*/


public abstract class BaseActivity<DB extends ViewDataBinding> extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    public DB dataBinding;

    /*public CatLoadingView mView ;*/
    @LayoutRes
    public abstract int getLayoutRes();

    Dialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes());
    }


    public void toast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }


    public void longToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new Dialog(this);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.progress_view_dialog);
        }
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }

    }

    public void hideProgress() {
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
    public AndroidInjector<Fragment> supportFragmentInjector() {
       return fragmentAndroidInjector;
    }

}
