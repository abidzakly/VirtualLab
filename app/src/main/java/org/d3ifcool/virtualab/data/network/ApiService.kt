package org.d3ifcool.virtualab.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.network.request.ExerciseCreate
import org.d3ifcool.virtualab.data.network.request.QuestionCreate
import org.d3ifcool.virtualab.data.network.request.UserLogin
import org.d3ifcool.virtualab.data.network.request.UserRegistration
import org.d3ifcool.virtualab.data.network.response.CombinedLatihan
import org.d3ifcool.virtualab.data.network.response.CombinedUser
import org.d3ifcool.virtualab.data.network.response.CombinedUsersResponse
import org.d3ifcool.virtualab.data.network.response.MessageResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://347d-139-228-112-175.ngrok-free.app"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    //    Auth
    @POST("/v1/users/create")
    suspend fun register(
        @Body user: UserRegistration
    ): MessageResponse

    @POST("/v1/users/login")
    suspend fun login(@Body user: UserLogin): CombinedUser

//    User
    @GET("/v1/users/pending")
    suspend fun getAllPendingUser(
        @Header("authorization") email: String
    ): List<CombinedUsersResponse>

    @GET("/v1/users/{user_id}")
    suspend fun getUserbyId(
        @Path("user_id") userId: Int
    ): CombinedUser

    @PUT("/v1/users/approve/{user_id}")
    suspend fun approveUser(
        @Header("authorization") email: String,
        @Path("user_id") userId: Int,
        @Query("password") password: String
    ): MessageResponse

    @DELETE("/v1/users/reject/{user_id}")
    suspend fun rejectUser(
        @Header("authorization") email: String,
        @Path("user_id") userId: Int
    ): MessageResponse

//    Latihan
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

    @GET("/v1/exercises/id/{exercise_id}")
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
        @Header("Authorization") email: String? = null
    ): MessageResponse
}

object UserApi {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
