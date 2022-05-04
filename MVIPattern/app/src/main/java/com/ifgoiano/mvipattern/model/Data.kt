package com.ifgoiano.mvipattern.model

import com.google.firebase.firestore.Exclude
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ifgoiano.mvipattern.util.Constants.Companion.FIELD

data class Data(
    var id: String? = null,
    @Expose
    @SerializedName(FIELD)
    var name: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
        )
    }
}
