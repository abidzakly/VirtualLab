package org.d3ifcool.virtualab.repository

import org.d3ifcool.virtualab.data.model.CombinedLatihan
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.QuestionCreate
import org.d3ifcool.virtualab.data.model.Soal
import org.d3ifcool.virtualab.data.network.apis.AuthorizedLatihanApi
import org.d3ifcool.virtualab.utils.ErrorMessage
import org.d3ifcool.virtualab.utils.Resource
import retrofit2.HttpException

class ExerciseRepository(
    private val latihanApi: AuthorizedLatihanApi
) {
    suspend fun addLatihan(content: ExerciseCreate): Resource<Latihan> {
        return try {
            val response = latihanApi.addLatihan(content)
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

    suspend fun getMyLatihan(): Resource<List<Latihan>> {
        return try {
            val response = latihanApi.getMyLatihan()
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

    suspend fun getDetailLatihan(latihanId: Int): Resource<LatihanDetail> {
        return try {
            val response = latihanApi.getDetailLatihan(latihanId)
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

    suspend fun deleteLatihan(latihanId: Int): Resource<MessageResponse> {
        return try {
            val response = latihanApi.deleteLatihan(latihanId)
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

    suspend fun addSoal(latihanId: Int, soal: List<QuestionCreate>): Resource<MessageResponse> {
        return try {
            val response = latihanApi.addSoal(latihanId, soal)
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

    suspend fun getSoalByExerciseId(latihanId: Int): Resource<List<CombinedLatihan>> {
        return try {
            val response = latihanApi.getSoalbyExerciseId(latihanId)
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

    suspend fun approveOrReject(latihanId: Int, status: String): Resource<MessageResponse> {
        return try {
            val response = latihanApi.modifyLatihanStatus(latihanId, status)
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