package org.d3ifcool.virtualab.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.virtualab.ui.screen.admin.CheckUsersViewModel
import org.d3ifcool.virtualab.ui.screen.admin.UsersInfoViewModel
import org.d3ifcool.virtualab.ui.screen.auth.AuthViewModel

class ViewModelFactory(private val email: String? = null, private val user_id: Int? = null): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            return CheckUsersViewModel(email!!) as T
        }
        if (modelClass.isAssignableFrom(UsersInfoViewModel::class.java)) {
            return UsersInfoViewModel(user_id!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}