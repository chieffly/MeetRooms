package ru.chieffly.meetroom.db

import androidx.room.*
import io.reactivex.Flowable
import ru.chieffly.meetroom.model.rooms.Meet

@Dao
interface MeetDao {
    @Query("SELECT * FROM meet")
    fun getall() : Flowable<List<Meet>>

    @Query("SELECT * FROM meet WHERE id = :id")
    fun getById(id: Long): Flowable<Meet>

    @Query("SELECT * FROM meet WHERE roomId = :room_id AND timeStart >:timeNow ORDER BY date DESC")
    fun getNextMeets(room_id: Long, timeNow: Long): Flowable<List<Meet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: Meet)

    @Update
    fun update(employee: Meet)

    @Delete
    fun delete(employee: Meet)
}
