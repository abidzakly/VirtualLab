package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.CombinedPost
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.CombinedUsers
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.PendingPosts
import org.d3ifcool.virtualab.data.model.UserUpdate
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthorizedUserApi {
    //    User
    @GET("/v1/users/pending")
    suspend fun getAllPendingUser(): List<CombinedUsers>

    @GET("/v1/users/{userId}")
    suspend fun getUserbyId(
        @Path("userId") userId: Int
    ): CombinedUser

    @PUT("/v1/users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Int,
        @Query("old_password") oldPassword: String,
        @Body user: UserUpdate
    ): MessageResponse

    @PUT("/v1/users/{userId}/approve")
    suspend fun approveUser(
        @Path("userId") userId: Int,
        @Query("password") password: String
    ): MessageResponse

    @DELETE("/v1/users/{userId}/reject")
    suspend fun rejectUser(
        @Path("userId") userId: Int
    ): MessageResponse

    @GET("/v1/users/{teacherId}/posts")
    suspend fun getRecentPosts(
        @Path("teacherId") id: Int
    ): List<CombinedPost>

    @GET("/v1/users/posts/pending")
    suspend fun getPendingPosts(): List<PendingPosts>
}
