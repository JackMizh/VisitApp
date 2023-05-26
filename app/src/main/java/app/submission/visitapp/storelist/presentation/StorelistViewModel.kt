package app.submission.visitapp.storelist.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.core.repository.MainRepository
import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.storelist.repository.StorelistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StorelistViewModel @Inject constructor(
    private val storelistRepository: StorelistRepository
): ViewModel(){

    suspend fun getdata():List<Stores> {
        return withContext(Dispatchers.IO) {
            return@withContext storelistRepository.getData()
        }
    }
}