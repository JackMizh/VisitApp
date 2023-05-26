package app.submission.visitapp.login.api

import app.submission.visitapp.login.models.LoginRequest
import app.submission.visitapp.login.models.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginAPI {

    @Multipart
    @POST("/api/sariroti_md/index.php/login/loginTest")
    suspend fun login(@Part("username") username: RequestBody, @Part("password") password: RequestBody) : Response<LoginResponse>
}