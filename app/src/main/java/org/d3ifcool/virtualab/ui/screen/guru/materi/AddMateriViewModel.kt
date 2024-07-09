package org.d3ifcool.virtualab.ui.screen.guru.materi

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3ifcool.virtualab.data.model.MateriItem
import org.d3ifcool.virtualab.data.model.MessageResponse
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.repository.MaterialRepository
import org.d3ifcool.virtualab.utils.Resource
import org.d3ifcool.virtualab.utils.getFileExtension
import java.io.File
import java.io.FileOutputStream

class AddMateriViewModel(
    private val materialId: Int? = null,
    private val materialRepository: MaterialRepository
) : ViewModel() {


    var materiData = MutableStateFlow<MateriItem?>(null)
        private set

    var materiId = MutableStateFlow<Int?>(null)
        private set

    var apiStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var uploadStatus = MutableStateFlow(ApiStatus.IDLE)
        private set

    var successMessage = MutableStateFlow<String?>(null)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    init {
        if (materialId != null) {
            materiId.value = materialId
            getMateriData()
        }
    }

    fun getMateriData() {
        viewModelScope.launch(Dispatchers.IO) {
            apiStatus.value = ApiStatus.LOADING
            when (val response = materialRepository.getDetailMateri(materialId!!)) {
                is Resource.Success -> {
                    materiData.value = response.data!!.materiItem
                    apiStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    apiStatus.value = ApiStatus.FAILED
                }
            }
        }
    }

    fun addOrUpdateMateri(
        materialId: Int? = null,
        title: String? = null,
        description: String? = null,
        uri: Uri? = null,
        mediaType: String? = null,
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
            val mediaTypePart = mediaType!!.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestBody)

            uploadStatus.value = ApiStatus.LOADING

            var response: Resource<MessageResponse>? = null

            if (!isUpdate) {
                if (inputStream != null) {
                    response =
                        materialRepository.addMateri(titlePart, mediaTypePart, descPart, body)
                }
            } else {
                Log.d("AddMateriVM", "URI: $uri")

                var bodyUpdate: MultipartBody.Part? = null
                if (inputStream != null) {
                    bodyUpdate = body
                }
                if (bodyUpdate != null) {
                    response = materialRepository.updateMateri(
                        materialId!!,
                        titlePart,
                        mediaTypePart,
                        descPart,
                        bodyUpdate
                    )
                } else {
                    response = materialRepository.updateMateri(
                        materialId!!,
                        titlePart,
                        null,
                        descPart,
                        null
                    )
                }
            }
            when (response!!) {
                is Resource.Success -> {
                    successMessage.value = response.data!!.message
                    uploadStatus.value = ApiStatus.SUCCESS
                }

                is Resource.Error -> {
                    errorMessage.value = response.message
                    uploadStatus.value = ApiStatus.FAILED
                }
            }
        }
    }


    fun clearStatus() {
        uploadStatus.value = ApiStatus.IDLE
        apiStatus.value = ApiStatus.IDLE
        errorMessage.value = null
        successMessage.value = null
    }
}