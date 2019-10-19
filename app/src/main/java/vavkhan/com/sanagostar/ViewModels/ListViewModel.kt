package vavkhan.com.sanagostar.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vavkhan.com.sanagostar.Net.ListRepo
import vavkhan.com.sanagostar.Net.Resource
import vavkhan.com.sanagostar.models.AddressListModel
import javax.inject.Inject

class ListViewModel @Inject constructor(internal var repo: ListRepo) : ViewModel() {
    val addressList: MutableLiveData<Resource<List<AddressListModel?>?>?>?
        get() = repo.addressList

}