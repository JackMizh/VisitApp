package app.submission.visitapp.storelist.repository

import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.login.repository.StoreRepository
import javax.inject.Inject

class StorelistRepository @Inject constructor(
    private val storeRepository: StoreRepository
){
    suspend fun getData():List<Stores>{
        return storeRepository.getRecords()
    }
}