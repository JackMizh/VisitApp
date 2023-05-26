package app.submission.visitapp.login.di

import app.submission.visitapp.login.api.LoginAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesLoginAPI(retrofitBuilder: Retrofit.Builder): LoginAPI {
        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        return retrofitBuilder.client(httpClient.build()).build().create(LoginAPI::class.java)
    }

    // NOTE: this is unsafe, normally I put it in a separate file and then access it later from BuildConfig
    companion object {
        const val BASE_URL = "https://keraton.indward.com"
    }
}