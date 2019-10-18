package ru.chieffly.meetroom.db

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.chieffly.meetroom.model.rooms.Meetroom

@Dao
interface MeetroomDao {
    @Query("SELECT * FROM meetroom")
    fun getall() : Flowable<List<Meetroom>>

    @Query("SELECT * FROM meetroom WHERE isFavorite = 1" )
    fun getFavs() : Flowable<List<Meetroom>>

    @Query("SELECT * FROM meetroom WHERE id = :id")
    fun getById(id: Long): Single<Meetroom>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(meetroom: Meetroom)

    @Update
    fun update(meetroom: Meetroom)

    @Delete
    fun delete(meetroom: Meetroom)
}
