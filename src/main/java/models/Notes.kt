package models

import com.google.gson.GsonBuilder

data class Notes(
    var uuid: String?,
    var userId: String?,
    var title: String?,
    var note: String?,
    var createTime: Long?,
    var sharedUsers: List<String>
){
    constructor():this(
        uuid = null,
        userId = null,
        title = null,
        note = null,
        createTime = null,
        sharedUsers = listOf()
    )

    override fun toString(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}
