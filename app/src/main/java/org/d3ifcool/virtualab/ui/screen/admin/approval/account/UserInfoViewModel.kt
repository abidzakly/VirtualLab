package org.d3ifcool.virtualab.ui.screen.admin.approval.account

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.response.CombinedUser
import org.d3ifcool.virtualab.data.network.response.MessageResponse
import retrofit2.HttpException

class UserInfoViewModel(private val userId: Int) : ViewModel() {

    private val _fetchedUser = MutableStateFlow<CombinedUser?>(null)
    val fetchedUser: StateFlow<CombinedUser?> = _fetchedUser

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    private val _approveResponse = MutableStateFlow<MessageResponse?>(null)
    val approveResponse: StateFlow<MessageResponse?> = _approveResponse

    private val _emailSent = MutableStateFlow(false)
    val emailSent: StateFlow<Boolean> = _emailSent

    private val _rejectResponse = MutableStateFlow<MessageResponse?>(null)
    val rejectResponse: StateFlow<MessageResponse?> = _rejectResponse

    init {
        getUsersInfo()
    }

    private fun getUsersInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fetchedUser.value = ApiService.userService.getUserbyId(userId)
                Log.d("FETCHED_USER", "Fetched User: ${_fetchedUser.value}")
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun approveUser(auth: String, userId: Int, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _approveResponse.value = ApiService.userService.approveUser(auth, userId, password)
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun rejectUser(auth: String, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _rejectResponse.value = ApiService.userService.rejectUser(auth, userId)
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun sendEmail(context: Context, email: String, password: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "Password Akun Anda")
            putExtra(Intent.EXTRA_TEXT, "Password untuk akun Virtual Lab Anda adalah:\n\n $password")
        }

        context.startActivity(Intent.createChooser(intent, "Send Email"))
        _emailSent.value = true
    }

    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

    fun generatePassword(): String {
        val upperCaseLetters = ('A'..'Z')
        val lowerCaseLetters = ('a'..'z')
        val specialCharacters = "!@_."
        val digits = ('0'..'9')

        val allCharacters =
            upperCaseLetters + lowerCaseLetters + specialCharacters.toCharArray() + digits

        val password = StringBuilder()
        password.append(upperCaseLetters.random())
        password.append(lowerCaseLetters.random())
        password.append(specialCharacters.random())
        password.append(digits.random())

        for (i in 4 until 8) {
            password.append(allCharacters.random())
        }

        return password.toString().toList().shuffled().joinToString("")
    }

}