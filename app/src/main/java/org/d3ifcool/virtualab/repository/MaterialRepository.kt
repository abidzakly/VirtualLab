package org.d3ifcool.virtualab.repository

import okhttp3.MultipartBody
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.apis.AuthorizedMateriApi
import org.d3ifcool.virtualab.utils.ErrorMessage
import org.d3ifcool.virtualab.utils.Resource
import retrofit2.HttpException

class MaterialRepository(
    private val materialApi: AuthorizedMateriApi
) {
    suspend fun addMateri(title: String, mediaType: String, description: String, file: MultipartBody.Part): Resource<MessageResponse> {
        return try {
            val response = materialApi.addMateri(title, mediaType, description, file)
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

    suspend fun getMyMateri(): Resource<List<Materi>> {
        return try {
            val response = materialApi.getMyMateri()
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

    suspend fun getDetailMateri(materialId: Int): Resource<Materi> {
        return try {
            val response = materialApi.getDetailmateri(materialId)
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

    suspend fun updateMateri(materialId: Int): Resource<MessageResponse> {
        return try {
            val response = materialApi.updateMateri(materialId)
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

    suspend fun approveOrReject(materialId: Int, status: String): Resource<MessageResponse> {
        return try {
            val response = materialApi.modifyMateriStatus(materialId, status)
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

    suspend fun deleteMateri(materialId: Int): Resource<MessageResponse> {
        return try {
            val response = materialApi.deleteMateri(materialId)
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
}