package org.d3ifcool.virtualab.ui.screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.UserRepository
import org.d3ifcool.virtualab.utils.Resource
import org.d3ifcool.virtualab.utils.toMultipartBody

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _apiStatus = MutableStateFlow(ApiStatus.IDLE)
    val apiStatus: MutableStateFlow<ApiStatus> = _apiStatus

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    fun update(
        userId: Int,
        oldPassword: String,
        newPassword: String? = null,
        newEmail: String? = null,
        picture: Bitmap? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _apiStatus.value = ApiStatus.LOADING
            val oldPasswordPart = oldPassword.toRequestBody("text/plain".toMediaTypeOrNull())
            val newPasswordPart = newPassword?.toRequestBody("text/plain".toMediaTypeOrNull())
            val newEmailPart = newEmail?.toRequestBody("text/plain".toMediaTypeOrNull())
            val response = if (picture != null) {
                userRepository.update(
                    userId,
                    oldPasswordPart,
                    newPasswordPart,
                    newEmailPart,
                    picture.toMultipartBody()
                )
            } else {
                userRepository.update(
                    userId,
                    oldPasswordPart,
                    newPasswordPart,
                    newEmailPart
                )
            }
            when (response) {
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

    fun clearStatus() {
        _apiStatus.value = ApiStatus.IDLE
    }

    fun clearErrorMsg() {
        _apiStatus.value = ApiStatus.IDLE
        _errorMsg.value = ""
    }
}