package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.CombinedLatihan
import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.QuestionCreate
import org.d3ifcool.virtualab.data.model.LatihanDetail
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
    suspend fun getMyMateri(): List<Latihan>


    //    Get Detail Latihan
    @GET("/v1/exercises/{exercise_id}")
    suspend fun getDetailLatihan(
        @Path("exercise_id") exerciseId: Int
    ): LatihanDetail


    //    deleteLatihan
    @DELETE("/v1/exercises/{exercise_id}")
    suspend fun deleteLatihan(
        @Path("exercise_id") id: Int
    ): MessageResponse

    //    Create Soal
    @POST("/v1/exercises/{exercise_id}/questions")
    suspend fun addSoal(
        @Path("exercise_id") id: Int,
        @Body soal: List<QuestionCreate>
    ): MessageResponse


    //    Get Soal by Exercise ID
    @GET("/v1/exercises/{exercise_id}/questions")
    suspend fun getSoalbyExerciseId(
        @Path("exercise_id") id: Int
    ): List<CombinedLatihan>


//    Approve/Reject for Admin or submit Soal for Guru
    @PUT("/v1/exercises/{exercise_id}/status")
    suspend fun modifyLatihanStatus(
        @Path("exercise_id") exerciseId: Int,
        @Query("status") status: String,
    ): MessageResponse

}