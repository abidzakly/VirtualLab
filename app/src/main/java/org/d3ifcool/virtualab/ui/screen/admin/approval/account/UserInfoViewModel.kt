package org.d3ifcool.virtualab.ui.screen.admin.approval.account

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

class UserInfoViewModel(private val userId: Int, private val userRepository: UserRepository) :
    ViewModel() {

    private val _fetchedUser = MutableStateFlow<CombinedUser?>(null)
    val fetchedUser: StateFlow<CombinedUser?> = _fetchedUser

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    private val _apiStatus = MutableStateFlow(ApiStatus.LOADING)
    val apiStatus: StateFlow<ApiStatus> = _apiStatus

    private val _response = MutableStateFlow<MessageResponse?>(null)
    val response: StateFlow<MessageResponse?> = _response

    private val _postStatus = MutableStateFlow(ApiStatus.IDLE)
    val postStatus: StateFlow<ApiStatus> = _postStatus

    init {
        getUsersInfo()
    }

    fun getUsersInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiStatus.value = ApiStatus.LOADING
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
            _postStatus.value = ApiStatus.LOADING
            when (val response = userRepository.approveUser(userId, password)) {
                is Resource.Success -> {
                    _response.value = response.data
                    _postStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    _errorMsg.value = response.message
                    _postStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun rejectUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _postStatus.value = ApiStatus.LOADING
            when (val response = userRepository.rejectUser(userId)) {
                is Resource.Success -> {
                    _response.value = response.data
                    _postStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    _errorMsg.value = response.message
                    _postStatus.value = ApiStatus.FAILED
                }
            }
        }
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