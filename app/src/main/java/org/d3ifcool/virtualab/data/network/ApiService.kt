package org.d3ifcool.virtualab.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3ifcool.virtualab.data.model.User
import org.d3ifcool.virtualab.data.network.request.UserLogin
import org.d3ifcool.virtualab.data.network.request.UserRegistration
import org.d3ifcool.virtualab.data.network.response.CombinedUserResponse
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

    @GET("/users/pending/")
    suspend fun getAllPendingUser(
        @Header("Authorization") email: String
    ): List<CombinedUsersResponse>

    @GET("/users/{user_id}")
    suspend fun getUserbyId(
        @Path("user_id") userId: Int
    ): CombinedUserResponse

    //    Auth
    @POST("/users/login/")
    suspend fun login(@Body user: UserLogin): User

    @POST("/users/create/")
    suspend fun register(
        @Body user: UserRegistration
    ): MessageResponse

    @PUT("/users/approve/")
    suspend fun approveUser(
        @Query("user_id") userId: Int,
        @Query("password") password: String
    ): MessageResponse

    @DELETE("/users/reject/")
    suspend fun rejectUser(): MessageResponse

}

object UserApi {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }