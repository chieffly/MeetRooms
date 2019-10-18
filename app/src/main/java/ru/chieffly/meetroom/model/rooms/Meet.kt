package ru.chieffly.meetroom.model.rooms

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Entity(foreignKeys = arrayOf(ForeignKey(
//        entity = Meetroom::class,
//        parentColumns = arrayOf("room_id"),
//        childColumns = arrayOf("roomId"),
//        onDelete = ForeignKey.CASCADE
//    )
//)
//)
@Entity
data class Meet (
    @PrimaryKey
    @SerializedName("id")
    val id : Long,
    @SerializedName("date")
    val date : String,
    @SerializedName("time_start")
    val timeStart : Long,
    @SerializedName("time_end")
    val timeEnd : Long,
    @SerializedName("room_id")
    val roomId : Long,
    @SerializedName("user_id")
    val userId : Long
) : Serializable