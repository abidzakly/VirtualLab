package org.d3ifcool.virtualab.ui.screen.guru.latihan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.data.network.UserApi
import retrofit2.HttpException

class LatihanListViewModel(userId: Int) : ViewModel() {

    private val _latihanList = MutableStateFlow<List<Latihan>?>(emptyList())
    val latihanList: StateFlow<List<Latihan>?> = _latihanList

    private val _apiStatus = MutableStateFlow(ApiStatus.LOADING)
    val apiStatus: StateFlow<ApiStatus> = _apiStatus

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing


    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        getLatihanByUser(userId)
    }

    private fun getLatihanByUser(userId: Int) {
        _apiStatus.value = ApiStatus.LOADING
        Log.d("HomeLatihanVM", "userId: $userId")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _latihanList.value = UserApi.service.getLatihanByAuthor(userId)
                _apiStatus.value = ApiStatus.SUCCESS
                Log.d("HomeLatihanVM", "latihan list: ${_latihanList.value}")
            } catch (e: HttpException) {
                _apiStatus.value = ApiStatus.FAILED
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }



    fun refreshData(userId: Int) {
        _isRefreshing.value = true
        getLatihanByUser(userId)
        _isRefreshing.value = false
    }


    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}