package org.d3ifcool.virtualab.ui.screen.admin.approval.content

import UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Latihan
import org.d3ifcool.virtualab.data.model.PendingPosts
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.utils.Resource
import retrofit2.http.POST

class CheckFileViewModel(private val userRepository: UserRepository): ViewModel() {

    var postData = MutableStateFlow(emptyList<PendingPosts>())
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set
    init {
        getPendingPosts()
    }

    fun getPendingPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = userRepository.getPendingPosts()) {
                is Resource.Success -> {
                    postData.value = response.data!!
                    apiStatus.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }
}