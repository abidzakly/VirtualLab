package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.LatihanDetail
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.utils.Resource

class LatihanListViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    private val _latihanDetailList = MutableStateFlow<List<LatihanDetail>?>(emptyList())
    val latihanDetailList: StateFlow<List<LatihanDetail>?> = _latihanDetailList

    private val _apiStatus = MutableStateFlow(ApiStatus.LOADING)
    val apiStatus: StateFlow<ApiStatus> = _apiStatus

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing


    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        getMyLatihan()
    }

    fun getMyLatihan() {
        _apiStatus.value = ApiStatus.LOADING
//        Log.d("HomeLatihanVM", "userId: $userId")
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = exerciseRepository.getMyLatihan()) {
                is Resource.Success -> {
                    _latihanDetailList.value = response.data
                    _apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    _errorMsg.value = response.message
                    _apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }


    fun refreshData() {
        _isRefreshing.value = true
        getMyLatihan()
        _isRefreshing.value = false
    }


    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}