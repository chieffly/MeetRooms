package ru.chieffly.meetroom.net

import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.chieffly.meetroom.model.auth.AuthInfoDto
import ru.chieffly.meetroom.model.auth.LoginUserRequestDto

interface AuthApi {
    @POST("/auth")
    fun loginRequest (@Body body: LoginUserRequestDto) : Flowable<AuthInfoDto>

    @POST("/auth/logout")
    fun logoutRequest (@Header("authorization") token: String) : Flowable<AuthInfoDto>
}