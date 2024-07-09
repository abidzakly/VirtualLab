package org.d3ifcool.virtualab.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3ifcool.virtualab.data.model.CombinedPost
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.CombinedUsers
import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.data.model.PendingPosts
import org.d3ifcool.virtualab.data.model.UserUpdate
import org.d3ifcool.virtualab.data.network.apis.AuthorizedUserApi
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.Resource
import org.d3ifcool.virtualab.utils.UserDataStore
import retrofit2.HttpException

class UserRepository(
    private val dataStore: UserDataStore,
    private val userApi: AuthorizedUserApi
) {

    suspend fun getPendingPosts(): Resource<List<PendingPosts>> {
        return try {
            val response = userApi.getPendingPosts()
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
                    422 -> GenericMessage.inputError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }

    suspend fun getRecentPosts(teacherId: Int): Resource<List<CombinedPost>> {
        return try {
            val response = userApi.getRecentPosts(teacherId)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
                    422 -> GenericMessage.inputError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }
    suspend fun getPendingUser(): Resource<List<CombinedUsers>> {
        return try {
            val response = userApi.getAllPendingUser()
            return Resource.Success(data = response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
                    422 -> GenericMessage.inputError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }

    suspend fun getUserById(userId: Int, isAdmin: Boolean): Resource<CombinedUser> {
        return try {
            val response = userApi.getUserbyId(userId)
            if (!isAdmin) {
                updateToDataStore(response)
            }
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
                    422 -> GenericMessage.inputError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }


    suspend fun update(userId: Int, oldPassword: RequestBody, newPassword: RequestBody? = null, newEmail: RequestBody? = null, file: MultipartBody.Part? = null): Resource<MessageResponse> {
        return try {
            val response = userApi.updateUser(userId, oldPassword, newPassword, newEmail, file)
            this.getUserById(userId, false)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
                    422 -> GenericMessage.inputError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }

    private suspend fun updateToDataStore(data: CombinedUser) {
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
        dataStore.saveData(
            user,
            student,
            teacher
        )
    }

    suspend fun approveUser(userId: Int, password: String): Resource<MessageResponse> {
        return try {
            val response = userApi.approveUser(userId, password)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
                    422 -> GenericMessage.inputError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }

    suspend fun rejectUser(userId: Int): Resource<MessageResponse> {
        return try {
            val response = userApi.rejectUser(userId)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
                    422 -> GenericMessage.inputError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }
}