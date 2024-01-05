package models

import com.google.gson.GsonBuilder

data class Session(
    var sessionId: String?,
    var userId: String?,
    var sessionStartTime: Long?,
    var requestRemaining: Long?,
    var token: String?
) {
    constructor(): this(
        sessionId = null,
        userId = null,
        sessionStartTime = null,
        requestRemaining = null,
        token = null
    )

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}