package app.submission.visitapp.storedetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.storedetail.models.Visit
import app.submission.visitapp.storedetail.repository.VisitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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