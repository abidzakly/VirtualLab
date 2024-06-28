package org.d3ifcool.virtualab.data.network.apis

import okhttp3.MultipartBody
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface MaterialApiService {
    @Multipart
    @POST("/v1/users/{teacherId}/materials")
    suspend fun register(
        @Path("teacherId") id: Int,
        @Part("title") title: String,
        @Part("media_type") mediaType: String,
        @Part("description") desc: String,
        @Part("file") file: MultipartBody.Part,
    ): MessageResponse

    @GET("/v1/materials/pending")
    suspend fun getPendingMateris(
        @Header("authorization") email: String
    ): List<Materi>

    @GET("/v1/materials/author/{author_id}")
    suspend fun getMaterisByAuthor(
        @Path("author_id") id: Int
    ): List<Materi>

    @GET("/v1/materials/{material_id")
    suspend fun getDetailmateri(
        @Path("material_id") id: Int
    ): Materi

    @PUT("/v1/materials/{material_id")
    suspend fun updateMateri(
        @Path("material_id") id: Int
    ): MessageResponse

    @PUT("/v1/materials/approve/{material_id")
    suspend fun approveMateri(
        @Path("material_id") id: Int,
        @Header("authorization") email: String
    ): MessageResponse

    @PUT("/v1/materials/reject/{material_id")
    suspend fun rejectMateri(
        @Path("material_id") id: Int,
        @Header("authorization") email: String
    ): MessageResponse


}
