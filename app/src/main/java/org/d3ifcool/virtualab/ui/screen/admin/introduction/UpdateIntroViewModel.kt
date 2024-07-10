package org.d3ifcool.virtualab.ui.screen.admin.introduction

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3ifcool.virtualab.data.model.Introduction
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.IntroRepository
import org.d3ifcool.virtualab.utils.Resource
import org.d3ifcool.virtualab.utils.getFileExtension
import java.io.File
import java.io.FileOutputStream

class UpdateIntroViewModel(private val introRepository: IntroRepository) : ViewModel() {

    var data = MutableStateFlow<Introduction?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set
    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = introRepository.getData()) {
                is Resource.Success -> {
                    data.value = response.data
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                }
            }
        }
    }

    fun addOrUpdateData(
        title: String? = null,
        description: String? = null,
        uri: Uri? = null,
        isUpdate: Boolean,
        contentResolver: ContentResolver
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val inputStream = uri?.let { contentResolver.openInputStream(it) }
            val fileExtension = uri?.let { getFileExtension(it, contentResolver) }
            val file = File.createTempFile("upload", ".$fileExtension", null)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)

            val titlePart = title!!.toRequestBody("text/plain".toMediaTypeOrNull())
            val descPart = description!!.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestBody = file.asRequestBody("video/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestBody)

            apiStatus.value = ApiStatus.LOADING

            var response: Resource<MessageResponse>? = null

            if (!isUpdate) {
                if (inputStream != null) {
                    response = introRepository.addData(titlePart, descPart, body)
                }
            } else {

                var bodyUpdate: MultipartBody.Part? = null
                if (inputStream != null) {
                    bodyUpdate = body
                }
                    response = introRepository.updateData(titlePart, descPart, bodyUpdate)
            }
            when (response!!) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun deleteData() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = introRepository.deleteData()) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    apiStatus.value = ApiStatus.SUCCESS
                }
                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }


    fun clearStatus() {
        apiStatus.value = ApiStatus.IDLE
        successMessage.value = null
        errorMessage.value = null
    }
}