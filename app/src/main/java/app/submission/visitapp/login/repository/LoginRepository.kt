package app.submission.visitapp.login.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import app.submission.visitapp.core.presentation.MainActivity
import app.submission.visitapp.login.api.LoginAPI
import app.submission.visitapp.login.presentation.LoginActivity
import app.submission.visitapp.login.utils.alertDialog
import okhttp3.RequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginAPI: LoginAPI,
    private val storeRepository: StoreRepository
    ){
    suspend fun login(username: RequestBody, password: RequestBody, context: Activity){
        val response = loginAPI.login(username, password)
        if(response.body()!!.status == "failure"){
            alertDialog(
                "Something Wrong",
                "Please check your internet connection and try again",
                context
            )
        }
        else{
            val listResponse = response.body()!!.stores
            storeRepository.insertRecords(listResponse)
            Handler(Looper.getMainLooper()).postDelayed({
                context.startActivity(Intent(context, MainActivity::class.java))
                context.finish()
            }, 3000)
        }
    }

    suspend fun check(context: Activity){
        val length = storeRepository.getRecords().size
        Log.d("length", length.toString())
        Handler(Looper.getMainLooper()).postDelayed({
            if(length != 0){
                context.startActivity(Intent(context, MainActivity::class.java))
                context.finish()
            }
            else{
                context.startActivity(Intent(context, LoginActivity::class.java))
                context.finish()
            }
        }, 3000)
    }
}