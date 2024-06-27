package org.d3ifcool.virtualab.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.virtualab.data.model.Guru
import org.d3ifcool.virtualab.data.model.Murid
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.data.model.User
import org.d3ifcool.virtualab.data.network.request.StudentCreate
import org.d3ifcool.virtualab.data.network.request.UserCreate
import org.d3ifcool.virtualab.data.network.request.UserLogin
import org.d3ifcool.virtualab.data.network.UserApi
import org.d3ifcool.virtualab.data.network.request.TeacherCreate
import org.d3ifcool.virtualab.data.network.request.UserRegistration
import org.d3ifcool.virtualab.data.network.response.CombinedUser
import org.d3ifcool.virtualab.data.network.response.MessageResponse
import retrofit2.HttpException

class AuthViewModel(private val dataStore: UserDataStore) : ViewModel() {

    private val _currentUser = MutableStateFlow<CombinedUser?>(null)
    val currentUser: StateFlow<CombinedUser?> = _currentUser

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _registerSuccess = MutableStateFlow<MessageResponse?>(null)
    val registerSuccess: MutableStateFlow<MessageResponse?> = _registerSuccess

    private val _errorMsg = MutableStateFlow<String?>("")
    val errorMsg: StateFlow<String?> = _errorMsg

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentUser.value = UserApi.service.login(UserLogin(username, password))
                _loginSuccess.value = true
                dataStore.saveData(
                    User(
                        full_name = _currentUser.value!!.user!!.full_name,
                        username = _currentUser.value!!.user!!.username,
                        user_type = _currentUser.value!!.user!!.user_type,
                        email = _currentUser.value!!.user!!.email,
                        password = _currentUser.value!!.user!!.password,
                        user_id = _currentUser.value!!.user!!.user_id,
                        school = _currentUser.value!!.user!!.school,
                    ),
                    _currentUser.value!!.student?.let {
                        Murid(
                            student_id = it.student_id,
                            nisn = it.nisn
                        )
                    },
                    _currentUser.value!!.teacher?.let {
                        Guru(
                            teacher_id = it.teacher_id,
                            nip = it.nip
                        )
                    }
                )
                dataStore.setLoginStatus(true)
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun register(uniqueId: String, user: UserCreate) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _registerSuccess.value =
                    UserApi.service.register(
                        UserRegistration(
                            student = StudentCreate(uniqueId),
                            teacher = TeacherCreate(uniqueId),
                            user = user
                        )
                    )
            } catch (e: HttpException) {
                _errorMsg.value =
                    e.response()?.errorBody()?.string()?.replace(Regex("""[{}":]+"""), "")
                        ?.replace("detail", "")
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveData(User(), Murid(), Guru())
            dataStore.setLoginStatus(false)
        }
    }


    fun clearErrorMsg() {
        _errorMsg.value = ""
    }

}