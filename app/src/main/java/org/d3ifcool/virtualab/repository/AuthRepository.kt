package org.d3ifcool.virtualab.repository

import android.util.Log
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.data.model.StudentCreate
import org.d3ifcool.virtualab.data.model.TeacherCreate
import org.d3ifcool.virtualab.data.model.User
import org.d3ifcool.virtualab.data.model.UserCreate
import org.d3ifcool.virtualab.data.model.UserLogin
import org.d3ifcool.virtualab.data.model.UserRegistration
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.apis.UnauthedApi
import org.d3ifcool.virtualab.utils.ErrorMessage
import org.d3ifcool.virtualab.utils.Resource
import org.d3ifcool.virtualab.utils.UserDataStore
import retrofit2.HttpException


class AuthRepository(
    private val dataStore: UserDataStore,
    private val unAuthedApi: UnauthedApi
) {

    suspend fun login(username: String, password: String): Resource<CombinedUser> {
        return try {
            val result = unAuthedApi.login(UserLogin(username, password))
            Log.d("AuthRepository", "Access Token : ${result.accessToken}")
            ApiService.createAuthorizedService(result.accessToken!!)
            saveToDataStore(result.accessToken, result)
            Resource.Success(result)
        } catch (e: HttpException) {
            val errorMessage =
                when (e.code()) {
                    500 -> ErrorMessage.applicationError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        }
    }

    private suspend fun saveToDataStore(accessToken: String, data: CombinedUser) {
        val user = data.user!!
        val student = data.student?.let {
            Murid(
                studentId = it.studentId,
                nisn = it.nisn
            )
        }
        val teacher = data.teacher?.let {
            Guru(
                teacherId = it.teacherId,
                nip = it.nip
            )
        }
        dataStore.saveToken(accessToken)
        dataStore.saveData(
            user,
            student,
            teacher
        )
        dataStore.setLoginStatus(true)
    }

    suspend fun register(uniqueId: String, user: UserCreate): Resource<MessageResponse> {
        return try {
            val response = unAuthedApi.register(
                UserRegistration(
                    student = StudentCreate(uniqueId),
                    teacher = TeacherCreate(uniqueId),
                    user = user
                )
            )
            Resource.Success(response)
        } catch (e: HttpException) {
            val errorMessage =
                when (e.code()) {
                    500 -> ErrorMessage.applicationError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        }
    }

    suspend fun logout() {
        dataStore.setLoginStatus(false)
        dataStore.saveToken("")
        dataStore.saveData(User())
    }
}