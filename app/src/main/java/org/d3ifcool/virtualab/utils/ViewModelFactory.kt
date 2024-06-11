package org.d3ifcool.virtualab.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.virtualab.ui.screen.admin.CheckUsersViewModel
import org.d3ifcool.virtualab.ui.screen.admin.UsersInfoViewModel
import org.d3ifcool.virtualab.ui.screen.auth.AuthViewModel

class ViewModelFactory(
    private val email: String? = null,
    private val user_id: Int? = null,
    private val userDataStore: UserDataStore? = null
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            CheckUsersViewModel(email!!) as T
        } else if (modelClass.isAssignableFrom(UsersInfoViewModel::class.java)) {
            UsersInfoViewModel(user_id!!) as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(userDataStore!!) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}