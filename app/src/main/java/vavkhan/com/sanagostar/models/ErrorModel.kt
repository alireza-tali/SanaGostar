package vavkhan.com.sanagostar.models

class ErrorModel : BaseModel {
    var errorCode = 0
    var errorMessage: String? = null
    var errorType: Int? = null

    constructor() {}
    constructor(errorCode: Int, errorMessage: String?, errorType: Int?) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
        this.errorType = errorType
    }

}