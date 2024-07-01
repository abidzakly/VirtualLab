package org.d3ifcool.virtualab.utils

import UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.virtualab.repository.AuthRepository
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.repository.MaterialRepository
import org.d3ifcool.virtualab.ui.screen.AuthViewModel
import org.d3ifcool.virtualab.ui.screen.ProfileViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.CheckUsersViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.UserInfoViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.CheckFileViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.FileInfoViewModel
import org.d3ifcool.virtualab.ui.screen.guru.dashboard.GuruDashboardViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddSoalViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.LatihanListViewModel
import org.d3ifcool.virtualab.ui.screen.guru.materi.DetailMateriViewModel
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriViewModel

class ViewModelFactory(
    private val id: Int? = null,
    private val str: String? = null,
    private val authRepository: AuthRepository? = null,
    private val userRepository: UserRepository? = null,
    private val materialRepository: MaterialRepository? = null,
    private val exerciseRepository: ExerciseRepository? = null
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            CheckUsersViewModel(userRepository!!) as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(authRepository!!) as T
        } else if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            return CheckUsersViewModel(userRepository!!) as T
        } else if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(id!!, userRepository!!) as T
        } else if (modelClass.isAssignableFrom(AddSoalViewModel::class.java)) {
            return AddSoalViewModel(id!!) as T
//        } else if (modelClass.isAssignableFrom(DetailLatihanViewModel::class.java)) {
//            return DetailLatihanViewModel(exerciseId!!, exerciseRepository!!) as T
        } else if (modelClass.isAssignableFrom(LatihanListViewModel::class.java)) {
            return LatihanListViewModel(exerciseRepository!!) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository!!) as T
        } else if (modelClass.isAssignableFrom(GuruDashboardViewModel::class.java)) {
            return GuruDashboardViewModel(id!!, userRepository!!) as T
        } else if (modelClass.isAssignableFrom(DetailMateriViewModel::class.java)) {
            return DetailMateriViewModel(id!!, materialRepository!!) as T
        } else if (modelClass.isAssignableFrom(GuruMateriViewModel::class.java)) {
            return GuruMateriViewModel(materialRepository!!) as T
        } else if (modelClass.isAssignableFrom(FileInfoViewModel::class.java)) {
            return FileInfoViewModel(id!!, str!!, materialRepository!!, exerciseRepository!!) as T
        } else if (modelClass.isAssignableFrom(CheckFileViewModel::class.java)) {
            return CheckFileViewModel(userRepository!!) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}