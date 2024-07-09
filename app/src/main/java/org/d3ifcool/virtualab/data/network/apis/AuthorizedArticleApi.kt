package org.d3ifcool.virtualab.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3ifcool.virtualab.data.model.Artikel
import org.d3ifcool.virtualab.data.model.ArtikelItem
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthorizedArticleApi {
    @Multipart
    @POST("/v1/articles")
    suspend fun addArticle(
        @Part("title") title: RequestBody,
        @Part("description") desc: RequestBody,
        @Part file: MultipartBody.Part
    ): MessageResponse

    @GET("/v1/articles")
    suspend fun getMyArticles(): List<ArtikelItem>

    @GET("/v1/articles/{articleId}")
    suspend fun getDetailArticle(
        @Path("articleId") id: Int
    ): Artikel

    @Multipart
    @PUT("/v1/articles/{articleId}")
    suspend fun updateArticle(
        @Path("articleId") id: Int,
        @Part("title") title: RequestBody? = null,
        @Part("description") desc: RequestBody? = null,
        @Part file: MultipartBody.Part? = null
    ): MessageResponse

    @DELETE("/v1/articles/{articleId}")
    suspend fun deleteArticle(
        @Path("articleId") id: Int
    ): MessageResponse

    @PUT("/v1/articles/{articleId}/status")
    suspend fun modifyArticleStatus(
        @Path("articleId") id: Int,
        @Query("status") status: String
    ): MessageResponse
}
