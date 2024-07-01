package org.d3ifcool.virtualab.ui.screen.guru.dashboard

import UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.CombinedPost
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.utils.Resource

class GuruDashboardViewModel(
    private val teacherId: Int,
    private val userRepository: UserRepository
) : ViewModel() {

    var combinedPosts = MutableStateFlow(emptyList<CombinedPost>())
        private set

    var apiStatus = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = userRepository.getRecentPosts(teacherId)) {
                is Resource.Success -> {
                    combinedPosts.value = response.data!!
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