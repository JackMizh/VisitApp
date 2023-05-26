package app.submission.visitapp.core.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.core.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel(){

    fun logout(context: Activity){
        viewModelScope.launch {
            mainRepository.delete(context)
        }
    }

    suspend fun getdata():Int {
        return withContext(Dispatchers.IO) {
            return@withContext mainRepository.getData()
        }
    }
}