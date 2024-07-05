package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.ApprovedLatihan
import org.d3ifcool.virtualab.data.model.ApprovedMateri
import org.d3ifcool.virtualab.data.model.NilaiDetail
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.SoalMurid
import org.d3ifcool.virtualab.data.model.Materi
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.model.Nilai
import org.d3ifcool.virtualab.data.model.SubmitJawaban
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthorizedStudentApi {
//    Get All Approved Materi
    @GET("/v1/materials/approved")
    suspend fun getMateris(): List<ApprovedMateri>

//    Get All Approved Latihans
    @GET("/v1/exercises/approved")
    suspend fun getLatihans(): List<ApprovedLatihan>

//    Submit Answer
    @POST("/v1/exercises/{exerciseId}/practice")
    suspend fun submitAnswers(
        @Path("exerciseId") id: Int,
        @Body answers: SubmitJawaban
    ): MessageResponse

//    Get Soal for Practice
    @GET("/v1/exercises/{exerciseId}/practice")
    suspend fun getSoalForPractice(
        @Path("exerciseId") id: Int
    ): List<SoalMurid>

//    Get My Results
    @GET("/v1/results")
    suspend fun getMyResults(): List<Nilai>

//    Get Result Detail
    @GET("/v1/results/{resultId}")
    suspend fun getResultDetail(
      @Path("resultId") id: Int
    ): NilaiDetail



}