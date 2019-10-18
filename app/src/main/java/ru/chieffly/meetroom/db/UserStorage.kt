package ru.chieffly.meetroom.utils

import android.content.Context
import ru.chieffly.meetroom.model.UserInfo
import ru.chieffly.meetroom.utils.PreferenceHelper.get
import ru.chieffly.meetroom.utils.PreferenceHelper.set
import ru.chieffly.meetroom.model.auth.AuthInfoDto

const val APP_PREFERENCES_ID = "id"
const val APP_PREFERENCES_USERNAME = "username"
const val APP_PREFERENCES_FIRSTNAME = "firstName"
const val APP_PREFERENCES_LASTNAME = "lastName"
const val APP_PREFERENCES_USERDESCR = "userDescription"
const val APP_PREFERENCES_TOKEN = "token"
const val APP_PREFERENCES_REFRESH_TOKEN = "refreshToken"

class UserStorage (cntxt: Context) {
    private val prefs = PreferenceHelper.customPrefs(cntxt, "meetroom_prefs")

    fun saveUserInfosaveUserInfo (user : UserInfo) {
        prefs[APP_PREFERENCES_ID] = user.id
        prefs[APP_PREFERENCES_USERNAME] = user.username
    }

    fun saveToken (token: String) {
        prefs[APP_PREFERENCES_TOKEN] = token
    }
    fun saveRefreshToken (token: String) {
        prefs[APP_PREFERENCES_REFRESH_TOKEN] = token
    }

    fun saveAuthorization (auth : AuthInfoDto) {
        saveToken (auth.access_token)
        saveUserInfosaveUserInfo (auth.userInfo)
    }

    fun getToken() : String {
        val token: String? =  prefs[APP_PREFERENCES_TOKEN]
        return token.toString()
    }

    fun getUserId() : Int {
        val userId: Int? =  prefs[APP_PREFERENCES_ID]
        return userId as Int
    }

    fun getField (key : String): String {
        val value: String? =  prefs[key]
        return value.toString()
    }

    fun clear () {
        prefs.edit().clear().apply()
    }
}

