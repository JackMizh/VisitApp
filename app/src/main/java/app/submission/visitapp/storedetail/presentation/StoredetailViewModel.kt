package app.submission.visitapp.storedetail.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.login.repository.LoginRepository
import app.submission.visitapp.storedetail.models.Visit
import app.submission.visitapp.storedetail.repository.VisitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class StoredetailViewModel @Inject constructor(
    private val visitRepository: VisitRepository
): ViewModel(){

    fun insert(visit: Visit){
        viewModelScope.launch {
            visitRepository.insertRecords(visit)
        }
    }

    suspend fun getdata():List<Visit> {
        return withContext(Dispatchers.IO) {
            return@withContext visitRepository.getRecords()
        }
    }
}