package ru.chieffly.meetroom.model.auth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReserveRespond (
    @SerializedName("id") //req_id
    val id : Long,
    @SerializedName("Status")
    val Status : String
) : Serializable