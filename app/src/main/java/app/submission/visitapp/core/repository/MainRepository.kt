package app.submission.visitapp.core.repository

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import app.submission.visitapp.login.presentation.LoginActivity
import app.submission.visitapp.login.repository.StoreRepository
import app.submission.visitapp.storedetail.repository.VisitRepository
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val storeRepository: StoreRepository,
    private val visitRepository: VisitRepository
){
    suspend fun delete(context: Activity){
        storeRepository.deleteRecords()
        visitRepository.deleteRecords()
        Handler(Looper.getMainLooper()).postDelayed({
            context.startActivity(Intent(context, LoginActivity::class.java))
            context.finish()
        }, 3000)
    }

    suspend fun getData():Int{
        return storeRepository.getRecords().size
    }
}