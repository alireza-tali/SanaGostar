package vavkhan.com.sanagostar.Net;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import vavkhan.com.sanagostar.SanaApplication;
import vavkhan.com.sanagostar.models.AddressListModel;

public class RegisterRepo {
    private Context mContext;
    @Inject
    public RegisterRepo(Context mContext){
        this.mContext = mContext;
    }

    public MutableLiveData<Resource<AddressListModel>> sendRegisterData(JSONObject params){
        MutableLiveData<Resource<AddressListModel>> mObservableProducts = new MediatorLiveData<>();
        MainRest<AddressListModel> mainRest = new MainRest<>(SanaApplication.Companion.get(mContext),AddressListModel.class);
        mainRest.postAuth(params,new RestAPIListener<AddressListModel>() {
            @Override
            public void Error(String message, @Nullable String code) {
                mObservableProducts.setValue(Resource.error(message,null));
            }

            @Override
            public void Success(AddressListModel message) {
                mObservableProducts.setValue(Resource.success(message));
            }
        });
        return mObservableProducts;
    }
}
