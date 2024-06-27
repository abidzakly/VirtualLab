package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.network.request.UserLogin
import org.d3ifcool.virtualab.data.network.request.UserRegistration
import org.d3ifcool.virtualab.data.network.response.CombinedUser
import org.d3ifcool.virtualab.data.network.response.CombinedUsersResponse
import org.d3ifcool.virtualab.data.network.response.MessageResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

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
}