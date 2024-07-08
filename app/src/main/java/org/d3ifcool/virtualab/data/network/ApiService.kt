package org.d3ifcool.virtualab.data.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.d3ifcool.virtualab.data.network.apis.AuthorizedIntroductionApi
import org.d3ifcool.virtualab.data.network.apis.AuthorizedUserApi
import org.d3ifcool.virtualab.data.network.apis.AuthorizedLatihanApi
import org.d3ifcool.virtualab.data.network.apis.AuthorizedMateriApi
import org.d3ifcool.virtualab.data.network.apis.AuthorizedStudentApi
import org.d3ifcool.virtualab.data.network.apis.UnauthedApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://347d-139-228-112-175.ngrok-free.app"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object ApiService {
    val unauthedService: UnauthedApi by lazy {
        retrofit.create(UnauthedApi::class.java)
    }
    var userService: AuthorizedUserApi? = null
        private set
    var latihanService: AuthorizedLatihanApi? = null
        private set

    var materiService: AuthorizedMateriApi? = null
        private set

    var introductionService: AuthorizedIntroductionApi? = null
        private set

    var studentService: AuthorizedStudentApi? = null
        private set

    fun createAuthorizedService(authorization: String) {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val requestWithAuthorization = original.newBuilder()
                    .header("Authorization", "Bearer $authorization")
                    .build()
                chain.proceed(requestWithAuthorization)
            })
        }.build()

        val videoClient = OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val requestWithAuthorization = original.newBuilder()
                    .header("Authorization", "Bearer $authorization")
                    .build()
                chain.proceed(requestWithAuthorization)
            }).readTimeout(10, TimeUnit.MINUTES).writeTimeout(10, TimeUnit.MINUTES)
        }.build()
//
//        val retrofitWithAuth = Retrofit.Builder()
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .baseUrl(BASE_URL)
//            .build()
        val retrofitWithMedia = Retrofit.Builder()
            .client(videoClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
        userService = retrofitWithMedia.create(AuthorizedUserApi::class.java)
        latihanService = retrofitWithMedia.create(AuthorizedLatihanApi::class.java)
        materiService = retrofitWithMedia.create(AuthorizedMateriApi::class.java)
        introductionService = retrofitWithMedia.create(AuthorizedIntroductionApi::class.java)
        studentService = retrofitWithMedia.create(AuthorizedStudentApi::class.java)
        Log.d("ApiService create", "retrofit with auth created!")
    }

    fun getMateriMedia(materialId: Int): String {
        return "$BASE_URL/v1/materials/$materialId/content"
    }
    fun getIntroductionMedia(): String {
        return "$BASE_URL/v1/introduction/content"
    }
    fun getIntroductionThumbnail(): String {
        return "$BASE_URL/v1/introduction/content/thumbnail"
    }
}

enum class ApiStatus { IDLE, LOADING, SUCCESS, FAILED }
