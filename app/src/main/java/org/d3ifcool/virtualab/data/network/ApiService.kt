package org.d3ifcool.virtualab.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3ifcool.virtualab.data.network.apis.LatihanApiService
import org.d3ifcool.virtualab.data.network.apis.UserApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://347d-139-228-112-175.ngrok-free.app"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object ApiService {
    val userService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
    val latihanService: LatihanApiService by lazy {
        retrofit.create(LatihanApiService::class.java)
    }
//    val materiService: UserApiService by lazy {
//        retrofit.create(UserApiService::class.java)
//    }
//    val kerjaLatihanService: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
