//package org.d3ifmgmp.virtualab.utils
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import org.d3ifmgmp.virtualab.ui.screen.auth.AuthViewModel
//
//class ViewModelFactory(): ViewModelProvider.Factory {
//
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
//            return AuthViewModel() as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}