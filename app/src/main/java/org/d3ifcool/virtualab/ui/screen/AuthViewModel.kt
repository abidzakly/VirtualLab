package org.d3ifcool.virtualab.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.data.model.User
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.StudentCreate
import org.d3ifcool.virtualab.data.model.TeacherCreate
import org.d3ifcool.virtualab.data.model.UserCreate
import org.d3ifcool.virtualab.data.model.UserLogin
import org.d3ifcool.virtualab.data.model.UserRegistration
import org.d3ifcool.virtualab.data.network.ApiStatus
import retrofit2.HttpException

class AuthViewModel(private val dataStore: UserDataStore) : ViewModel() {

    private val _currentUser = MutableStateFlow<CombinedUser?>(null)
    val currentUser: StateFlow<CombinedUser?> = _currentUser

    private val _apiStatus = MutableStateFlow(ApiStatus.IDLE)
    val apiStatus: MutableStateFlow<ApiStatus> = _apiStatus

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    fun login(username: String, password: String) {
        _apiStatus.value = ApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentUser.value = ApiService.userService.login(UserLogin(username, password))
                saveToDataStore(_currentUser.value!!)
                apiStatus.value = ApiStatus.SUCCESS
            } catch (e: HttpException) {
                _errorMsg.value =
                    when (e.code()) {
                        500 -> "Terjadi Kesalahan. Harap coba lagi."
                        else -> {
                            e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                                ?.replace("detail", "")
                        }
                    }
                _apiStatus.value = ApiStatus.FAILED
            }
        }
    }

    private fun saveToDataStore(data: CombinedUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = data.user!!
            val student = data.student?.let {
                Murid(
                    student_id = it.student_id,
                    nisn = it.nisn
                )
            }
            val teacher = data.teacher?.let {
                Guru(
                    teacher_id = it.teacher_id,
                    nip = it.nip
                )
            }
            dataStore.saveData(
                user,
                student,
                teacher
            )
            dataStore.setLoginStatus(true)
        }
    }

    fun register(uniqueId: String, user: UserCreate) {
        _apiStatus.value = ApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.userService.register(
                    UserRegistration(
                        student = StudentCreate(uniqueId),
                        teacher = TeacherCreate(uniqueId),
                        user = user
                    )
                )

                if (response.status) {
                    _apiStatus.value = ApiStatus.SUCCESS
                }
            } catch (e: HttpException) {
                _errorMsg.value =
                    when (e.code()) {
                        500 -> "Terjadi Kesalahan. Harap coba lagi."
                        else -> {
                            e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                                ?.replace("detail", "")
                        }
                    }
                _apiStatus.value = ApiStatus.FAILED
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveData(User(), Murid(), Guru())
            dataStore.setLoginStatus(false)
        }
    }

    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}