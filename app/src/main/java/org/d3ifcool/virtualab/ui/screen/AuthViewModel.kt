package org.d3ifcool.virtualab.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.UserCreate
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.AuthRepository
import org.d3ifcool.virtualab.utils.Resource

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<CombinedUser?>(null)
    val currentUser: StateFlow<CombinedUser?> = _currentUser

    private val _apiStatus = MutableStateFlow(ApiStatus.IDLE)
    val apiStatus: MutableStateFlow<ApiStatus> = _apiStatus

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _apiStatus.value = ApiStatus.LOADING
            when (val response = authRepository.login(username, password)) {
                is Resource.Success -> {
                    Log.d(
                        "AuthVM",
                        "User: ${response.data!!.user!!}\nGuru: ${response.data.teacher}\nMurid: ${response.data.student}"
                    )
                    Log.d("AuthVM", "Token: ${response.data.accessToken}")
                    _currentUser.value = response.data
                    _apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    _errorMsg.value = response.message
                    _apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }


    fun register(uniqueId: String, user: UserCreate) {
        _apiStatus.value = ApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = authRepository.register(uniqueId, user)) {
                is Resource.Success -> {
                    _apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    _errorMsg.value = response.message
                    _apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.logout()
        }
    }

    fun clearStatus() {
        _apiStatus.value = ApiStatus.IDLE
        _errorMsg.value = ""
    }

}