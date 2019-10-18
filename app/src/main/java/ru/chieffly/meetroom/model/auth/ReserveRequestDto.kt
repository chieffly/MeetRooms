package ru.chieffly.meetroom.model.auth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReserveRequestDto (
        @SerializedName("timeStart")
        val dateAndTimeStart : Long,
        @SerializedName("timeEnd")
        val dateAndTimeEnd : Long,
        @SerializedName("roomId")
        val roomId : Long,
        @SerializedName("userId")
        val userId : Long
    ) : Serializable
