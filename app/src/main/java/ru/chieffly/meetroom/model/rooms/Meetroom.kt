package ru.chieffly.meetroom.model.rooms

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Meetroom (
    @PrimaryKey
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("seats")
    val seats : Int,
    @SerializedName("projector")
    val hasProjector : Boolean,
    @SerializedName("markerboard")
    val hasBoard : Boolean,
    @SerializedName("isFavorite")
    var isFavorite : Boolean
) : Serializable
