package org.d3ifcool.virtualab.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.model.MateriItem
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.apis.AuthorizedMateriApi
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.Resource
import retrofit2.HttpException

class ArticleRepository(
    private val materialApi: AuthorizedMateriApi
) {
    suspend fun addMateri(
        title: RequestBody,
        mediaType: RequestBody,
        description: RequestBody,
        file: MultipartBody.Part
    ): Resource<MessageResponse> {
        return try {
            val response = materialApi.addMateri(title, mediaType, description, file)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
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

    suspend fun getMyMateri(): Resource<List<MateriItem>> {
        return try {
            val response = materialApi.getMyMateri()
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
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

    suspend fun getDetailMateri(materialId: Int): Resource<Materi> {
        return try {
            val response = materialApi.getDetailMateri(materialId)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
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

    suspend fun updateMateri(
        materialId: Int,
        title: RequestBody? = null,
        mediaType: RequestBody? = null,
        description: RequestBody? = null,
        file: MultipartBody.Part? = null
        ): Resource<MessageResponse> {
        return try {
            val response = materialApi.updateMateri(materialId, title, mediaType, description, file)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
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

    suspend fun approveOrReject(materialId: Int, status: String): Resource<MessageResponse> {
        return try {
            val response = materialApi.modifyMateriStatus(materialId, status)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
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

    suspend fun deleteMateri(materialId: Int): Resource<MessageResponse> {
        return try {
            val response = materialApi.deleteMateri(materialId)
            Resource.Success(response)
        } catch (e: HttpException) {
            e.printStackTrace()
            val errorMessage =
                when (e.code()) {
                    500 -> GenericMessage.applicationError
                    413 -> GenericMessage.applicationError
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