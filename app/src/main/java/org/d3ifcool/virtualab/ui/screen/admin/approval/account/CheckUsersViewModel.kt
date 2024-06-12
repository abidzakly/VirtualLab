package org.d3ifcool.virtualab.ui.screen.admin.approval.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.data.network.UserApi
import org.d3ifcool.virtualab.data.network.response.CombinedUsersResponse

import retrofit2.HttpException

class CheckUsersViewModel(userEmail: String) : ViewModel() {

    private val _userList = MutableStateFlow<List<CombinedUsersResponse?>>(emptyList())
    val userList: StateFlow<List<CombinedUsersResponse?>> = _userList

    private val _isLoading = MutableStateFlow(ApiStatus.LOADING)
    val isLoading: StateFlow<ApiStatus> = _isLoading

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        getAllPendingUser(userEmail)
    }

    private fun getAllPendingUser(email: String) {
        _isLoading.value = ApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userList.value = UserApi.service.getAllPendingUser(email)
                _isLoading.value = ApiStatus.SUCCESS
                Log.d("GET PENDING USER SUCCESS", "Get User: $_userList")
            } catch (e: HttpException) {
                _isLoading.value = ApiStatus.FAILED
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

}
