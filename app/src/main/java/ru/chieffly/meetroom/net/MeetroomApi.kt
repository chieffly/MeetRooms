package ru.chieffly.meetroom.net

import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.chieffly.meetroom.model.auth.ReserveRequestDto
import ru.chieffly.meetroom.model.rooms.Meet
import ru.chieffly.meetroom.model.rooms.Meetroom

interface MeetroomApi {

//    @POST("/auth/login")
//    fun loginRequest (@Body body: LoginUserRequestDto) : Flowable<AuthInfoDto>
//
//    @POST("/auth/logout")
//    fun logoutRequest (@Header("authorization") token: String) : Flowable<AuthInfoDto>

    @GET("/rooms")
    fun getRooms () : Flowable<List<Meetroom>>

    @GET("/meets")
    fun getMeets () : Flowable<List<Meet>>

    @POST("/reserve_room")
    fun reserveRoom (@Body body: ReserveRequestDto) : Flowable<List<Meet>>
}