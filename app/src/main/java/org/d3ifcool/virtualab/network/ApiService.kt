package org.d3ifcool.virtualab.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3ifcool.virtualab.model.User
import org.d3ifcool.virtualab.network.create.UserLogin
import org.d3ifcool.virtualab.network.create.UserRegistration
import org.d3ifcool.virtualab.network.response.CombinedUser
import org.d3ifcool.virtualab.network.response.CombinedUsersResponse
import org.d3ifcool.virtualab.network.response.MessageResponse
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

private const val BASE_URL = "https://0ee9-139-228-112-175.ngrok-free.app"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
//    @GET("/users/{user_id}")
//    suspend fun getCurrentUser(@Path("user_id") id: Int): User

    @GET("/v1/users/pending")
    suspend fun getAllPendingUser(
        @Header("Authorization") email: String
    ): List<CombinedUsersResponse>

    @GET("/v1/users/{user_id}")
    suspend fun getUserbyId(
        @Path("user_id") userId: Int
    ): CombinedUser

    //    Auth
    @POST("/v1/users/login")
    suspend fun login(@Body user: UserLogin): User

    @POST("/v1/users/create")
    suspend fun register(
        @Body user: UserRegistration
    ): MessageResponse

    @PUT("/v1/users/approve/{user_id}")
    suspend fun approveUser(
        @Path("user_id") userId: Int,
        @Query("password") password: String,
    ): MessageResponse

    @DELETE("/v1/users/reject/{user_id}")
    suspend fun rejectUser(
        @Path("user_id") userId: Int,
    ): MessageResponse

}

object UserApi {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
