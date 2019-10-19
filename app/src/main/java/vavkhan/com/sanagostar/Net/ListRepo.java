package vavkhan.com.sanagostar.Net;

import android.app.Application;
import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import vavkhan.com.sanagostar.SanaApplication;
import vavkhan.com.sanagostar.models.AddressListModel;

public class ListRepo {
    private Context mContext;

    @Inject
    public ListRepo(Context mContext) {
        this.mContext = mContext;
    }

    public MutableLiveData<Resource<List<AddressListModel>>> getAddressList(){
        MutableLiveData<Resource<List<AddressListModel>>> mObservableProducts = new MediatorLiveData<>();
        MainRest<AddressListModel> mainRest = new MainRest<>(SanaApplication.Companion.get(mContext),AddressListModel.class);
        mainRest.getListAuth(new RestApiListListener<AddressListModel>() {
            @Override
            public void Error(String message, @Nullable String code) {
                mObservableProducts.setValue(Resource.error(message,null));
            }

            @Override
            public void Success(List<AddressListModel> message) {
                mObservableProducts.setValue(Resource.success(message));
            }
        });
        return mObservableProducts;
    }
}
