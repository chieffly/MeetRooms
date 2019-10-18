package ru.chieffly.meetroom.model.auth

import com.google.gson.annotations.SerializedName
import ru.chieffly.meetroom.model.UserInfo

data class AuthInfoDto (
    @SerializedName("access_token") val access_token : String,
    @SerializedName("token_type") val token_type : String,
    @SerializedName("expires_in") val expires_in : Long,
    @SerializedName("refresh_token") val refresh_token : String,
    @SerializedName("userInfo") val userInfo : UserInfo
)