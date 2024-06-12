package org.d3ifcool.virtualab.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.CheckUsersViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.UserInfoViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddSoalViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.DetailLatihanViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.LatihanListViewModel

class ViewModelFactory(
    private val email: String? = null,
    private val userId: Int? = null,
    private val exerciseId: Int? = null
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            return CheckUsersViewModel(email!!) as T
        } else if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(userId!!) as T
        } else if (modelClass.isAssignableFrom(AddSoalViewModel::class.java)) {
            return AddSoalViewModel(exerciseId!!) as T
        } else if (modelClass.isAssignableFrom(DetailLatihanViewModel::class.java)) {
            return DetailLatihanViewModel(exerciseId!!) as T
        } else if (modelClass.isAssignableFrom(LatihanListViewModel::class.java)) {
            return LatihanListViewModel(userId!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}