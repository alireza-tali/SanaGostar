package vavkhan.com.sanagostar.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import vavkhan.com.sanagostar.models.SendAddressModel;
import vavkhan.com.sanagostar.R;
import vavkhan.com.sanagostar.databinding.RegisterFragmentBinding;

public class RegisterFragment extends BaseFragment<RegisterFragmentBinding>{
    @Inject
    Context appContext;

    SendAddressModel model = new SendAddressModel();
    @Override
    public int getLayoutRes() {
        return R.layout.register_fragment;
    }

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        model.setGender("female");
        model = ((RegisterActivity)activity).getAddressModel();
        if (model != null){
            dataBinding.addressEdttxt.setText(model.getAddress());
            dataBinding.nameEdttxt.setText(model.getFirstName());
            dataBinding.phoneEdttxt.setText(model.getMobileNumber());
            dataBinding.lastnameEdttxt.setText(model.getLastName());
            dataBinding.genderSwitch.setSelectedTab(model.getGender().equals("male")?1:0);
        }else{
            model = new SendAddressModel();
        }
        dataBinding.genderSwitch.setOnSwitchListener((position, tabText) -> {
            if (position == 0){
                model.setGender("male");
            }else{
                model.setGender("female");
            }
        });

        dataBinding.continiueBtn.setOnClickListener(view->{
            if (validation()){
                sendData();
            }
        });
        return dataBinding.getRoot();
    }

    private void sendData() {
        model.setAddress(dataBinding.addressEdttxt.getText().toString().trim());
        model.setFirstName(dataBinding.nameEdttxt.getText().toString());
        model.setLastName(dataBinding.lastnameEdttxt.getText().toString());
        model.setMobileNumber(dataBinding.phoneEdttxt.getText().toString());
        ((RegisterActivity)activity).setRegisterModl(model);
        ((RegisterActivity)activity).changeToMap();
    }

    private boolean validation() {
        if (dataBinding.nameEdttxt.getText().toString().trim().isEmpty()){
            dataBinding.nameEdttxt.setError("لطفا نام را وارد نمایید");
            return false;
        }

        if (dataBinding.lastnameEdttxt.getText().toString().trim().isEmpty()){
            dataBinding.lastnameEdttxt.setError("لطفا نام خانوادگی را وارد نمایید");
            return false;
        }
        if (dataBinding.addressEdttxt.getText().toString().trim().isEmpty()){
            dataBinding.addressEdttxt.setError("لطفا آدرس را وارد نمایید");
            return false;
        }
        if (dataBinding.phoneEdttxt.getText().toString().trim().isEmpty() || dataBinding.phoneEdttxt.getText().toString().trim().length() < 11){
            dataBinding.phoneEdttxt.setError("لطفا شماره موبایل صحیح را وارد نمایید");
            return false;
        }
        return true;
    }


}
