package org.d3ifcool.virtualab.data.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3ifcool.virtualab.data.model.Introduction
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface AuthorizedIntroductionApi {
//    Create Introduction
    @Multipart
    @POST("/v1/introduction")
    suspend fun addIntroduction(
    @Part("title") title: RequestBody,
    @Part("description") desc: RequestBody,
    @Part file: MultipartBody.Part
    ): MessageResponse

//    Get Introduction
    @GET("/v1/introduction")
    suspend fun getIntroduction(): Introduction

//    Update Introduction
    @Multipart
    @PUT("/v1/introduction")
    suspend fun updateIntroduction(
        @Part("title") title: RequestBody? = null,
        @Part("description") desc: RequestBody? = null,
        @Part file: MultipartBody.Part? = null
    ): MessageResponse

//    Delete Introduction
    @DELETE("/v1/introduction")
    suspend fun deleteIntroduction(): MessageResponse

}