package org.d3ifcool.virtualab.ui.screen.admin.approval.account

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.CombinedUser
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.UserRepository
import org.d3ifcool.virtualab.utils.Resource

class UserInfoViewModel(private val userId: Int, private val userRepository: UserRepository) : ViewModel() {

    private val _fetchedUser = MutableStateFlow<CombinedUser?>(null)
    val fetchedUser: StateFlow<CombinedUser?> = _fetchedUser

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    private val _apiStatus = MutableStateFlow(ApiStatus.LOADING)
    val apiStatus: StateFlow<ApiStatus> = _apiStatus

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
                when (val response = userRepository.getUserById(userId, true)) {
                    is Resource.Success -> {
                        _fetchedUser.value = response.data
                        _apiStatus.value = ApiStatus.SUCCESS
                    }
                    is Resource.Error -> {
                        _errorMsg.value = response.message
                        _apiStatus.value = ApiStatus.FAILED
                    }
                }
        }
    }

    fun approveUser(userId: Int, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = userRepository.approveUser(userId, password)) {
                is Resource.Success -> {
                    _approveResponse.value = response.data
                }
                is Resource.Error -> {
                    _errorMsg.value = response.message
                }
            }
        }
    }

    fun rejectUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = userRepository.rejectUser(userId)) {
                is Resource.Success -> {
                    _rejectResponse.value = response.data
                }
                is Resource.Error -> {
                    _errorMsg.value = response.message
                }
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