package org.d3ifcool.virtualab.ui.screen.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.network.UserApi
import org.d3ifcool.virtualab.network.response.CombinedUsersResponse
import retrofit2.HttpException

class CheckUsersViewModel(userEmail: String) : ViewModel() {

        private val _userList = MutableStateFlow<List<CombinedUsersResponse?>>(emptyList())
    val userList: StateFlow<List<CombinedUsersResponse?>> = _userList

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        getAllPendingUser(userEmail)
    }

    private fun getAllPendingUser(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userList.value = UserApi.service.getAllPendingUser(email)
                Log.d("GET USER SUCCESS", "Get User: $_userList")
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

}
