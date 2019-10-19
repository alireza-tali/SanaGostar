package vavkhan.com.sanagostar.views;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import vavkhan.com.sanagostar.models.AddressListModel;
import vavkhan.com.sanagostar.Net.Status;
import vavkhan.com.sanagostar.R;
import vavkhan.com.sanagostar.ViewModels.ListViewModel;
import vavkhan.com.sanagostar.databinding.ListActivityBinding;
import vavkhan.com.sanagostar.views.Adapters.AddressListAdapter;
import vavkhan.com.sanagostar.views.Adapters.ListClick;

public class ActivityList extends BaseActivity<ListActivityBinding> implements LifecycleOwner, ListClick<AddressListModel> {
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ListViewModel listViewModel;
    @Inject
    Context appContext;
    AddressListAdapter adapter;
    @Override
    public int getLayoutRes() {
        return R.layout.list_activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(dataBinding.navView.getId(), DrawerFragment.newInstance(dataBinding.drawerLayout)).commit();
        dataBinding.toggleDrawer.setOnClickListener(view ->{
            dataBinding.drawerLayout.openDrawer(Gravity.RIGHT);
        });
        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        dataBinding.include.addressList.setLayoutManager(new LinearLayoutManager(appContext, LinearLayoutManager.VERTICAL,false));
        adapter = new AddressListAdapter(this);
        dataBinding.include.addressList.setAdapter(adapter);
        getData();
    }

    private void getData(){
        showProgress();
        listViewModel.getAddressList().observe(this,response->{
            hideProgress();
            if (response.status == Status.ERROR){
                toast(appContext,response.message);
            }else{
                showdata(response.data);
            }
        });
    }

    private void showdata(List<AddressListModel> data) {
        adapter.setData(data);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        if (lifecycleRegistry == null)
            lifecycleRegistry = new LifecycleRegistry(this);
        return lifecycleRegistry;
    }

    @Override
    public void onListClicked(AddressListModel drawerUserModel, View sharedView) {
        toast(this,drawerUserModel.getFullName());
    }
}
