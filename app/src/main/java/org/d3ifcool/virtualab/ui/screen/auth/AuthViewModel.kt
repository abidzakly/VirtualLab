package org.d3ifcool.virtualab.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.User
import org.d3ifcool.virtualab.data.network.request.StudentCreate
import org.d3ifcool.virtualab.data.network.request.UserCreate
import org.d3ifcool.virtualab.data.network.request.UserLogin
import org.d3ifcool.virtualab.data.network.UserApi
import org.d3ifcool.virtualab.data.network.request.UserRegistration
import org.d3ifcool.virtualab.data.network.response.MessageResponse
import retrofit2.HttpException

class AuthViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _fetchStatus = MutableStateFlow(false)
    val fetchStatus: StateFlow<Boolean> = _fetchStatus

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _registerSuccess = MutableStateFlow<MessageResponse?>(null)
    val registerSuccess: MutableStateFlow<MessageResponse?> = _registerSuccess


    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentUser.value = UserApi.service.login(UserLogin(username, password))
                _loginSuccess.value = true
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun register(uniqueId: String, user: UserCreate) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _registerSuccess.value =
                    UserApi.service.register(
                        UserRegistration(
                            student = StudentCreate(uniqueId),
                            user = user
                        )
                    )
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun clearFetchStatus() {
        _fetchStatus.value = false
    }

    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}