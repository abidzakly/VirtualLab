package org.d3ifcool.virtualab.ui.screen

import UserRepository
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
import org.d3ifcool.virtualab.utils.Resource
import org.d3ifcool.virtualab.utils.UserDataStore
import retrofit2.HttpException

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _apiStatus = MutableStateFlow(ApiStatus.IDLE)
    val apiStatus: MutableStateFlow<ApiStatus> = _apiStatus

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    fun update(userId: Int, oldPassword: String, update: UserUpdate) {
        _apiStatus.value = ApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = userRepository.update(userId, oldPassword, update)) {
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
        _errorMsg.value = ""
    }
}