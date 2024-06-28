package org.d3ifcool.virtualab.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.virtualab.ui.screen.AuthViewModel
import org.d3ifcool.virtualab.ui.screen.ProfileViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.CheckUsersViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.UserInfoViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddSoalViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.DetailLatihanViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.LatihanListViewModel

class ViewModelFactory(
    private val email: String? = null,
    private val user_id: Int? = null,
    private val userDataStore: UserDataStore? = null,
    private val exerciseId: Int? = null
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            CheckUsersViewModel(email!!) as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(userDataStore!!) as T
        } else if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            return CheckUsersViewModel(email!!) as T
        } else if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(user_id!!) as T
        } else if (modelClass.isAssignableFrom(AddSoalViewModel::class.java)) {
            return AddSoalViewModel(exerciseId!!) as T
        } else if (modelClass.isAssignableFrom(DetailLatihanViewModel::class.java)) {
            return DetailLatihanViewModel(exerciseId!!) as T
        } else if (modelClass.isAssignableFrom(LatihanListViewModel::class.java)) {
            return LatihanListViewModel(user_id!!) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userDataStore!!) as T
        } else {
        throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}