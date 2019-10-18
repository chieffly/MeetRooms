package ru.chieffly.meetroom.model

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("id") val id : Int,
    @SerializedName("username") val username : String
)