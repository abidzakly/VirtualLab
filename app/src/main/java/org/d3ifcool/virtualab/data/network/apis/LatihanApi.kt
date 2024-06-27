package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.network.request.ExerciseCreate
import org.d3ifcool.virtualab.data.network.request.QuestionCreate
import org.d3ifcool.virtualab.data.network.response.CombinedLatihan
import org.d3ifcool.virtualab.data.network.response.MessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface LatihanApiService {
    @POST("/v1/exercises/create")
    suspend fun addLatihan(
        @Body content: ExerciseCreate
    ): Latihan

    @POST("/v1/exercises/questions")
    suspend fun addSoal(
        @Body soal: List<QuestionCreate>
    ): MessageResponse

    @GET("/v1/exercises/pending")
    suspend fun getPendingLatihan(): List<Latihan>

    @GET("/v1/exercises/{exercise_id}")
    suspend fun getCurrentLatihan(
        @Path("exercise_id") exerciseId: Int
    ): CombinedLatihan

    @GET("/v1/exercises/author/{author_id}")
    suspend fun getLatihanByAuthor(
        @Path("author_id") authorId: Int
    ): List<Latihan>

    @PUT("/v1/exercises/status/{exercise_id}")
    suspend fun modifyLatihanStatus(
        @Path("exercise_id") exerciseId: Int,
        @Query("status") status: String,
        @Header("authorization") email: String? = null
    ): MessageResponse
}