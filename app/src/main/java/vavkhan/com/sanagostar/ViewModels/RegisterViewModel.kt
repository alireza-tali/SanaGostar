package vavkhan.com.sanagostar.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vavkhan.com.sanagostar.Net.RegisterRepo
import vavkhan.com.sanagostar.Net.Resource
import vavkhan.com.sanagostar.models.AddressListModel
import vavkhan.com.sanagostar.models.SendAddressModel
import javax.inject.Inject

class RegisterViewModel @Inject constructor(internal var registerRepo: RegisterRepo) : ViewModel() {
    var sendAddressModel: SendAddressModel? = null
    fun sendData(): MutableLiveData<Resource<AddressListModel?>?>? {
        return registerRepo.sendRegisterData(sendAddressModel!!.toJson())
    }

}