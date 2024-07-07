package org.d3ifcool.virtualab.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3ifcool.virtualab.data.model.Introduction
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.apis.AuthorizedIntroductionApi
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.Resource
import retrofit2.HttpException

class IntroRepository(
    private val introApi: AuthorizedIntroductionApi
) {
    suspend fun addData(
        title: RequestBody,
        description: RequestBody,
        file: MultipartBody.Part
    ): Resource<MessageResponse> {
        return try {
            val response = introApi.addIntroduction(title, description, file)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
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


    suspend fun getData(): Resource<Introduction> {
        return try {
            val response = introApi.getIntroduction()
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
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

    suspend fun updateData(
        title: RequestBody? = null,
        description: RequestBody? = null,
        file: MultipartBody.Part? = null
    ): Resource<MessageResponse> {
        return try {
            val response = introApi.updateIntroduction(title, description, file)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
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

    suspend fun deleteData(): Resource<MessageResponse> {
        return try {
            val response = introApi.deleteIntroduction()
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    else -> {
                        e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                            ?.replace("detail", "")
                    }
                }
            Resource.Error(errorMessage!!)
        } catch (e: Exception) {
            e.printStackTrace()
            e.printStackTrace()
            Resource.Error(GenericMessage.noInternetError)
        }
    }
}