//package org.d3ifcool.virtualab.repository
//
//import android.util.Log
//import org.d3ifcool.virtualab.data.model.CombinedUser
//import org.d3ifcool.virtualab.data.model.Guru
//import org.d3ifcool.virtualab.data.model.MessageResponse
//import org.d3ifcool.virtualab.data.model.Murid
//import org.d3ifcool.virtualab.data.model.UserLogin
//import org.d3ifcool.virtualab.data.network.ApiService
//import retrofit2.HttpException
//
//class UserRepository(
//    private val userApi: ApiService
//) {
//    suspend fun login(request: UserLogin): CombinedUser {
//        return try {
//            userApi.userService.login(UserLogin(request.username, request.password))
//        } catch (e: HttpException) {
//            when (e.code()) {
//                500 -> {
//                    CombinedUser(status = false, message = "Terjadi Kesalahan. Harap coba lagi")
//                }
//                else -> {
//                    Log.d("UserRepository - Login", "Stack Trace: ${e.printStackTrace()}")
//                    val messsage = e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
//                        ?.replace("detail", "")
//                    CombinedUser(status = false, message = messsage!!)
//                }
//            }
//        }
//    }
//
//    suspend fun getUserById(userId: Int): CombinedUser {
//        return try {
//            userApi.userService.getUserbyId(userId)
//        } catch (e: HttpException) {
//            when (e.code()) {
//                500 -> {
//                    CombinedUser(status = false, message = "Terjadi Kesalahan. Harap Coba Lagi")
//                }
//                else -> {
//                    Log.d("UserRepository - Get User", "Stack Trace: ${e.printStackTrace()}")
//                    val messsage = e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
//                        ?.replace("detail", "")
//                    CombinedUser(status = false, message = messsage!!)
//                }
//            }
//        }
//    }
//}