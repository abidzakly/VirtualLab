package org.d3ifcool.virtualab.repository

import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.ExerciseUpdate
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.QuestionCreateOrUpdate
import org.d3ifcool.virtualab.data.network.apis.AuthorizedLatihanApi
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.Resource
import retrofit2.HttpException

class ExerciseRepository(
    private val latihanApi: AuthorizedLatihanApi
) {
    suspend fun addLatihan(latihan: ExerciseCreate): Resource<MessageResponse> {
        return try {
            val response = latihanApi.addLatihan(latihan)
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

    suspend fun getMyLatihan(): Resource<List<LatihanDetail>> {
        return try {
            val response = latihanApi.getMyLatihan()
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

    suspend fun getDetailLatihan(exerciseId: Int): Resource<Latihan> {
        return try {
            val response = latihanApi.getDetailLatihan(exerciseId)
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

    suspend fun updateLatihan(exerciseId: Int, isResettingResults: Boolean = false, isUpdatingSoal: Boolean, latihan: ExerciseUpdate): Resource<MessageResponse> {
        return try {
            val response = latihanApi.updateLatihan(exerciseId, isResettingResults, isUpdatingSoal, latihan)
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

    suspend fun deleteLatihan(exerciseId: Int): Resource<MessageResponse> {
        return try {
            val response = latihanApi.deleteLatihan(exerciseId)
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

    suspend fun addSoal(exerciseId: Int, soal: List<QuestionCreateOrUpdate>): Resource<MessageResponse> {
        return try {
            val response = latihanApi.addSoal(exerciseId, soal)
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

    suspend fun updateSoal(exerciseId: Int, soal: List<QuestionCreateOrUpdate>): Resource<MessageResponse> {
        return try {
            val response = latihanApi.updateSoal(exerciseId, soal)
            Resource.Success(response)
        }  catch (e: HttpException) {
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

    suspend fun approveOrReject(latihanId: Int, status: String): Resource<MessageResponse> {
        return try {
            val response = latihanApi.modifyLatihanStatus(latihanId, status)
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