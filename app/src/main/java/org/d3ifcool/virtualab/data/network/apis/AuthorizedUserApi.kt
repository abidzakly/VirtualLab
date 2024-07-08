package org.d3ifcool.virtualab.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3ifcool.virtualab.data.model.CombinedPost
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.CombinedUsers
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.PendingPosts
import org.d3ifcool.virtualab.data.model.UserUpdate
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @Multipart
    @PUT("/v1/users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Int,
        @Part("old_password") oldPassword: RequestBody,
        @Part("new_password") newPassword: RequestBody? = null,
        @Part("new_email") newEmail: RequestBody? = null,
        @Part file: MultipartBody.Part? = null
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
