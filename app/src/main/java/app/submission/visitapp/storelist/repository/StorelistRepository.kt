package app.submission.visitapp.storelist.repository

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.login.presentation.LoginActivity
import app.submission.visitapp.login.repository.StoreRepository
import javax.inject.Inject

class StorelistRepository @Inject constructor(
    private val storeRepository: StoreRepository
){
    suspend fun getData():List<Stores>{
        return storeRepository.getRecords()
    }
}