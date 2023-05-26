package app.submission.visitapp.login.di

import android.app.Application
import app.submission.visitapp.login.dao.RoomDao
import app.submission.visitapp.login.database.StoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {

    @Singleton
    @Provides
    fun getStoreDatabase(context: Application): StoreDatabase {
        return StoreDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getStoreDao(storeDatabase: StoreDatabase): RoomDao {
        return storeDatabase.storesDao()
    }
}