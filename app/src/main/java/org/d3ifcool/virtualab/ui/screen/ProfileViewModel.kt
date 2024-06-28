package org.d3ifcool.virtualab.ui.screen

import android.util.Log
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.data.model.UserUpdate
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.utils.UserDataStore
import retrofit2.HttpException

class ProfileViewModel(private val dataStore: UserDataStore) : ViewModel() {

    private val _apiStatus = MutableStateFlow(ApiStatus.IDLE)
    val apiStatus: MutableStateFlow<ApiStatus> = _apiStatus

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    fun update(userId: Int, oldPassword: String, update: UserUpdate) {
        _apiStatus.value = ApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.userService.updateUser(userId, oldPassword, update)

                if (response.status) {
                    retrieveData(userId)
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
                Log.d("ProfileVM", "Update Error: ${_errorMsg.value}")
                _apiStatus.value = ApiStatus.FAILED
            }
        }
    }

    private fun retrieveData(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.userService.getUserbyId(userId)
                updateToDataStore(response)
            } catch (e: HttpException) {
                _errorMsg.value =
                    when (e.code()) {
                        500 -> "Terjadi Kesalahan. Harap coba lagi."
                        else -> {
                            e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                                ?.replace("detail", "")
                        }
                    }
            }
        }
    }

    private fun updateToDataStore(data: CombinedUser) {
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
        }
    }

    fun clearStatus() {
        _apiStatus.value = ApiStatus.IDLE
    }

    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}