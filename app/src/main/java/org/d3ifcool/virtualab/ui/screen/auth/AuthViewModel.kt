package org.d3ifcool.virtualab.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.model.User
import org.d3ifcool.virtualab.network.UserApi

class AuthViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _user.value = UserApi.service.login(UserLogin(username, password))
                _loginSuccess.value = true
            } catch (e: Exception) {
                Log.d("Login Failure", "Failure: ${e.message}")
            }
        }
    }

}