package vavkhan.com.sanagostar.models

import com.google.gson.annotations.SerializedName

class AddressListModel : BaseModel {
    var id: Long = 0
    @SerializedName("first_name")
    var firstName: String? = null
    @SerializedName("last_name")
    var lastName: String? = null
    @SerializedName("coordinate_mobile")
    var mobileNumber: String? = null
    var address: String? = null

    constructor() {}
    constructor(id: Long, firstName: String?, lastName: String?, mobileNumber: String?, address: String?) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.mobileNumber = mobileNumber
        this.address = address
    }

    val fullName: String
        get() = "$firstName $lastName"

}