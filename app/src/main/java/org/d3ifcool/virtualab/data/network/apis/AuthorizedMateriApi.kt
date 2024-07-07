package org.d3ifcool.virtualab.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.model.MateriItem
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthorizedMateriApi {
    @Multipart
    @POST("/v1/materials")
    suspend fun addMateri(
        @Part("title") title: RequestBody,
        @Part("media_type") mediaType: RequestBody,
        @Part("description") desc: RequestBody,
        @Part file: MultipartBody.Part
    ): MessageResponse

    @GET("/v1/materials")
    suspend fun getMyMateri(): List<MateriItem>

    @GET("/v1/materials/{materialId}")
    suspend fun getDetailmateri(
        @Path("materialId") id: Int
    ): Materi

    @Multipart
    @PUT("/v1/materials/{materialId}")
    suspend fun updateMateri(
        @Path("materialId") id: Int,
        @Part("title") title: RequestBody? = null,
        @Part("media_type") mediaType: RequestBody? = null,
        @Part("description") desc: RequestBody? = null,
        @Part file: MultipartBody.Part? = null
    ): MessageResponse

    @DELETE("/v1/materials/{materialId}")
    suspend fun deleteMateri(
        @Path("materialId") id: Int
    ): MessageResponse

    @PUT("/v1/materials/{materialId}/status")
    suspend fun modifyMateriStatus(
        @Path("materialId") id: Int,
        @Query("status") status: String
    ): MessageResponse
}
