package app.submission.visitapp.storedetail.di

import android.app.Application
import app.submission.visitapp.storedetail.dao.VisitDao
import app.submission.visitapp.storedetail.database.VisitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VisitModule {

    @Singleton
    @Provides
    fun getVisitDatabase(context: Application): VisitDatabase {
        return VisitDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getVisitDao(visitDatabase: VisitDatabase): VisitDao {
        return visitDatabase.visitdao()
    }
}