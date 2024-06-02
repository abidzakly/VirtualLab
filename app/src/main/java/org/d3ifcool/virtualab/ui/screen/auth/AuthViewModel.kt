package org.d3ifcool.virtualab.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.d3ifcool.virtualab.firebase.UserRepository
import org.d3ifcool.virtualab.model.User

class AuthViewModel : ViewModel() {
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _isRegistrationSuccessful = MutableStateFlow(false)
    val isRegistrationSuccessful: StateFlow<Boolean> = _isRegistrationSuccessful

    private val _registrationErrorMessage = MutableStateFlow<String?>(null)
    val registrationErrorMessage: StateFlow<String?> = _registrationErrorMessage

    fun registerUser(user: User, password: String) {
//        viewModelScope.launch {
//            auth.createUserWithEmailAndPassword(user.email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val firebaseUser = auth.currentUser
//                        user.id = firebaseUser?.uid ?: ""
//                        saveUserToFirestore(user)
//                    } else {
//                        _registrationErrorMessage.value = task.exception?.message
//                    }
//                }
//        }
    }

    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful: StateFlow<Boolean> = _isLoginSuccessful

    private val _loginErrorMessage = MutableStateFlow<String?>(null)
    val loginErrorMessage: StateFlow<String?> = _loginErrorMessage

    fun loginUser(email: String, password: String) {
//        viewModelScope.launch {
//            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _isLoginSuccessful.value = true
//                } else {
//                    val errorMessage = when (val exception = task.exception) {
//                        is FirebaseAuthException -> when (exception.errorCode) {
//                            "ERROR_INVALID_EMAIL" -> "Email tidak valid."
//                            "ERROR_USER_NOT_FOUND" -> "Email tidak ditemukan."
//                            "ERROR_WRONG_PASSWORD" -> "Password salah."
//                            "ERROR_USER_DISABLED" -> "Akun dinonaktifkan."
//                            "ERROR_TOO_MANY_REQUESTS" -> "Terlalu banyak percobaan. Coba lagi nanti."
//                            else -> "Kesalahan autentikasi."
//                        }
//
//                        else -> exception?.message ?: "Kesalahan tidak diketahui."
//                    }
//                    _loginErrorMessage.value = errorMessage
//                }
//            }
//        }
    }

    // Update user profile
    private val userRepository = UserRepository()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser


    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> get() = _updateSuccess

    private val _updateError = MutableStateFlow<String?>(null)
    val updateError: StateFlow<String?> get() = _updateError

    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess: StateFlow<Boolean> = _logoutSuccess


    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
//        val userId = userRepository.auth.currentUser?.uid
//        userId?.let {
//            fetchUserData(it)
//        }
    }


    fun fetchUserData(userId: String) {
//        viewModelScope.launch {
//            try {
//                val user = userRepository.getUserById(userId)
//                _currentUser.value = user
//            } catch (e: Exception) {
//                _updateError.value = e.message
//                Log.d("UserViewModel", "Error fetching user data: ${e.message}")
//            }
//        }
    }

    private fun saveUserToFirestore(user: User) {
//        firestore.collection("users").document("3")
//            .set(user)
//            .addOnSuccessListener {
//                _isRegistrationSuccessful.value = true
//            }
//            .addOnFailureListener { e ->
//                _registrationErrorMessage.value = e.message
//                Log.e("UserViewModel", "Error saving user to Firestore: ${e.message}")
//            }
    }

    fun resetLogoutState(){
        _logoutSuccess.value = false
    }

    fun logout(navController: NavHostController) {
//        val firebaseAuth = FirebaseAuth.getInstance()
//        firebaseAuth.signOut()
//        val authStateListener = FirebaseAuth.AuthStateListener {
//            if (it.currentUser == null) {
//                Log.d(TAG, "Anda Telah Keluar")
//                _logoutSuccess.value = true
//                navController.navigate(Screen.Role.route) {
//                    popUpTo(0) {inclusive = true }
//                }
//            } else {
//                Log.d(TAG, "Gagal Keluar")
//            }
//        }
//        firebaseAuth.addAuthStateListener(authStateListener)
    }
}