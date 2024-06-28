package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.ExerciseCreate
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.QuestionCreate
import org.d3ifcool.virtualab.data.model.Soal
import org.d3ifcool.virtualab.data.model.CombinedLatihan
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface LatihanApiService {
//    Create Latihan
    @POST("/v1/exercises/create")
    suspend fun addLatihan(
        @Body content: ExerciseCreate
    ): Latihan


//    Create Soal
    @POST("/v1/exercises/questions/{exercise_id}")
    suspend fun addSoal(
        @Path("exercise_id") id: Int,
        @Body soal: List<QuestionCreate>
    ): MessageResponse


//    Get Pending Latihan
    @GET("/v1/exercises/pending")
    suspend fun getPendingLatihan(
        @Header("authorization") email: String
    ): List<Latihan>


//    Get Detail Latihan
    @GET("/v1/exercises/{exercise_id}")
    suspend fun getCurrentLatihan(
        @Path("exercise_id") exerciseId: Int
    ): CombinedLatihan


//    Get Latihan by Author
    @GET("/v1/exercises/author/{author_id}")
    suspend fun getLatihanByAuthor(
        @Path("author_id") authorId: Int
    ): List<Latihan>


//    Get Soal by Exercise ID
    @GET("/v1/exercises/questions/{exercise_id}")
    suspend fun getSoalbyExerciseId(
        @Path("exercise_id") id: Int
    ): List<Soal>

//    Approve/Reject for Admin or submit Soal for Guru
    @PUT("/v1/exercises/status/{exercise_id}")
    suspend fun modifyLatihanStatus(
        @Path("exercise_id") exerciseId: Int,
        @Query("status") status: String,
        @Header("authorization") email: String? = null
    ): MessageResponse

//    deleteLatihan
    @DELETE("/v1/exercises/delete/{exercise_id}")
    suspend fun deleteLatihan(
        @Path("exercise_id") id: Int
    ): MessageResponse
}