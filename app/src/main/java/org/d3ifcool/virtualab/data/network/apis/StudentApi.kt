package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.UserLogin
import org.d3ifcool.virtualab.data.model.UserRegistration
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.CombinedUsersResponse
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentApiService {
    //    Auth
    @POST("/v1/auth/register")
    suspend fun register(
        @Body user: UserRegistration
    ): MessageResponse

    @POST("/v1/auth/login")
    suspend fun login(@Body user: UserLogin): CombinedUser

    //    User
    @GET("/v1/users/pending")
    suspend fun getAllPendingUser(
        @Header("authorization") email: String
    ): List<CombinedUsersResponse>

    @GET("/v1/users/{userId}")
    suspend fun getUserbyId(
        @Path("userId") userId: Int
    ): CombinedUser

    @PUT("/v1/users/{userId}")
    suspend fun editProfile(
        @Path("userId") userId: Int,
        @Query("password") password: String
    ): MessageResponse

    @PUT("/v1/users/{userId}/approve")
    suspend fun approveUser(
        @Header("authorization") email: String,
        @Path("userId") userId: Int,
        @Query("password") password: String
    ): MessageResponse

    @DELETE("/v1/users/{userId}/reject")
    suspend fun rejectUser(
        @Header("authorization") email: String,
        @Path("userId") userId: Int
    ): MessageResponse
}