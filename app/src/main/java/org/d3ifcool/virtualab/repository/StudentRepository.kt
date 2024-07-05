package org.d3ifcool.virtualab.repository

import org.d3ifcool.virtualab.data.model.ApprovedLatihan
import org.d3ifcool.virtualab.data.model.ApprovedMateri
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.Nilai
import org.d3ifcool.virtualab.data.model.NilaiDetail
import org.d3ifcool.virtualab.data.model.SoalMurid
import org.d3ifcool.virtualab.data.model.SubmitJawaban
import org.d3ifcool.virtualab.data.network.apis.AuthorizedStudentApi
import org.d3ifcool.virtualab.utils.GenericMessage
import org.d3ifcool.virtualab.utils.Resource
import retrofit2.HttpException

class StudentRepository(private val studentApi: AuthorizedStudentApi) {
    suspend fun submitAnswers(exerciseId: Int, answers: SubmitJawaban): Resource<MessageResponse> {
        return try {
            val response = studentApi.submitAnswers(exerciseId, answers)
            Resource.Success(response)
        } catch (e: HttpException) {
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

    suspend fun getSoal(exerciseId: Int): Resource<List<SoalMurid>> {
        return try {
            val response = studentApi.getSoalForPractice(exerciseId)
            Resource.Success(response)
        } catch (e: HttpException) {
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

    suspend fun getMyResults(): Resource<List<Nilai>> {
        return try {
            val response = studentApi.getMyResults()
            Resource.Success(response)
        } catch (e: HttpException) {
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

    suspend fun getResultDetail(resultId: Int): Resource<NilaiDetail> {
        return try {
            val response = studentApi.getResultDetail(resultId)
            Resource.Success(response)
        } catch (e: HttpException) {
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

    suspend fun getApprovedMateris(): Resource<List<ApprovedMateri>> {
        return try {
            val response = studentApi.getMateris()
            Resource.Success(response)
        } catch (e: HttpException) {
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

    suspend fun getApprovedLatihans(): Resource<List<ApprovedLatihan>> {
        return try {
            val response = studentApi.getLatihans()
            Resource.Success(response)
        } catch (e: HttpException) {
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
}