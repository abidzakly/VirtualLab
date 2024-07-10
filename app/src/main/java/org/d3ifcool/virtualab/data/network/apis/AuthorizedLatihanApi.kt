package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.ExerciseUpdate
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.data.model.QuestionCreateOrUpdate
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthorizedLatihanApi {
    //    Create Latihan
    @POST("/v1/exercises")
    suspend fun addLatihan(
        @Body content: ExerciseCreate
    ): MessageResponse

    //    Get Latihan by Author
    @GET("/v1/exercises")
    suspend fun getMyLatihan(): List<LatihanDetail>


    //    Get Detail Latihan
    @GET("/v1/exercises/{exercise_id}")
    suspend fun getDetailLatihan(
        @Path("exercise_id") exerciseId: Int
    ): Latihan


    //    Update Latihan
    @PUT("/v1/exercises/{exercise_id}")
    suspend fun updateLatihan(
        @Path("exercise_id") exerciseId: Int,
        @Query("is_resetting_results") isResetting: Boolean = false,
        @Query("is_updating_soal") isUpdating: Boolean,
        @Body exerciseUpdate: ExerciseUpdate
    ): MessageResponse

    //    Delete Latihan
    @DELETE("/v1/exercises/{exercise_id}")
    suspend fun deleteLatihan(
        @Path("exercise_id") id: Int
    ): MessageResponse


    //    Create Soal
    @POST("/v1/exercises/{exercise_id}/questions")
    suspend fun addSoal(
        @Path("exercise_id") id: Int,
        @Body soal: List<QuestionCreateOrUpdate>
    ): MessageResponse


    //    Update Soal by Exercise ID
    @PUT("/v1/exercises/{exercise_id}/questions")
    suspend fun updateSoal(
        @Path("exercise_id") id: Int,
        @Body soal: List<QuestionCreateOrUpdate>
    ): MessageResponse


    //    Approve/Reject for Admin or submit Soal for Guru
    @PUT("/v1/exercises/{exercise_id}/status")
    suspend fun modifyLatihanStatus(
        @Path("exercise_id") exerciseId: Int,
        @Query("status") status: String,
    ): MessageResponse

}