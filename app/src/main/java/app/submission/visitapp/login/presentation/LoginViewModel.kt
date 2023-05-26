package app.submission.visitapp.login.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel(){

    fun session(context: Activity){
        viewModelScope.launch {
            loginRepository.check(context)
        }
    }

    fun login(username: RequestBody, password:RequestBody, context: Activity){
        viewModelScope.launch {
            loginRepository.login(username, password, context)
        }
    }
}