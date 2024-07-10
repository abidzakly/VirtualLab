package org.d3ifcool.virtualab.data.network.apis

import org.d3ifcool.virtualab.data.model.UserLogin
import org.d3ifcool.virtualab.data.model.UserRegistration
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.MessageResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UnauthedApi {
    //    Auth
    @POST("/v1/auth/register")
    suspend fun register(
        @Body user: UserRegistration
    ): MessageResponse

    @POST("/v1/auth/login")
    suspend fun login(@Body user: UserLogin): CombinedUser
}