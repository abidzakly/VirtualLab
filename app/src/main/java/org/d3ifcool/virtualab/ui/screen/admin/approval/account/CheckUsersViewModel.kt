package org.d3ifcool.virtualab.ui.screen.admin.approval.account

import UserRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.model.CombinedUsers
import org.d3ifcool.virtualab.utils.Resource

import retrofit2.HttpException

class CheckUsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableStateFlow<List<CombinedUsers?>>(emptyList())
    val userList: StateFlow<List<CombinedUsers?>> = _userList

    private val _isLoading = MutableStateFlow(ApiStatus.IDLE)
    val isLoading: StateFlow<ApiStatus> = _isLoading

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getAllPendingUser()
    }

    fun getAllPendingUser() {
        _isLoading.value = ApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = userRepository.getPendingUser()) {
                is Resource.Success -> {
                    _userList.value = response.data!!
                    _isLoading.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    _errorMsg.value = response.message
                    _isLoading.value = ApiStatus.FAILED
                }
            }
        }
    }

}
