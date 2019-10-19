package vavkhan.com.sanagostar.models

import com.google.gson.annotations.SerializedName

class SendAddressModel : BaseModel {
    var region = 0
    var address: String? = null
    @SerializedName("lat")
    var latitude: String? = null
    @SerializedName("lng")
    var longitude: String? = null
    @SerializedName("coordinate_mobile")
    var mobileNumber: String? = null
    @SerializedName("first_name")
    var firstName: String? = null
    @SerializedName("last_name")
    var lastName: String? = null
    var gender: String? = null

    constructor() {}
    constructor(region: Int, address: String?, latitude: String?, longitude: String?,
                mobileNumber: String?, firstName: String?, lastName: String?, gender: String?) {
        this.region = region
        this.address = address
        this.latitude = latitude
        this.longitude = longitude
        this.mobileNumber = mobileNumber
        this.firstName = firstName
        this.lastName = lastName
        this.gender = gender
    }

}