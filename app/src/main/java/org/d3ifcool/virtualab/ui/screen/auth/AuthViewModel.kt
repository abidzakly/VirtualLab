package org.d3ifcool.virtualab.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.model.User
import org.d3ifcool.virtualab.network.create.StudentCreate
import org.d3ifcool.virtualab.network.create.UserCreate
import org.d3ifcool.virtualab.network.create.UserLogin
import org.d3ifcool.virtualab.network.UserApi
import org.d3ifcool.virtualab.network.create.UserRegistration
import org.d3ifcool.virtualab.network.response.MessageResponse
import org.d3ifcool.virtualab.utils.UserDataStore
import retrofit2.HttpException

class AuthViewModel(private val dataStore: UserDataStore) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

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
                dataStore.setUserId(_currentUser.value!!.user_id)
                dataStore.setUserFullName(_currentUser.value!!.full_name)
                dataStore.setUserType(_currentUser.value!!.user_type)
                dataStore.setUserEmail(_currentUser.value!!.email)
                dataStore.setLoginStatus(true)
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


    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}