package models

import com.google.gson.GsonBuilder

data class AuthenticateRequest(
    var email: String?,
    var password: String?
) {
    constructor(): this(
        email = null,
        password = null
    )

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}