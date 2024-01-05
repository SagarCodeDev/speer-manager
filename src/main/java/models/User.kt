package models

import com.google.gson.GsonBuilder

data class User(
    var uuid: String?,
    var firstName: String?,
    var lastName: String?,
    var emailId: String?,
    var password: String?,
    var dob: Long?,
    var contactNumber: String?,
    var createTime: Long?,
    var country: String?
){
    constructor(): this(
        uuid = null,
        firstName = null,
        lastName = null,
        emailId = null,
        password = null,
        dob = null,
        contactNumber = null,
        createTime = null,
        country = null
    )

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}
