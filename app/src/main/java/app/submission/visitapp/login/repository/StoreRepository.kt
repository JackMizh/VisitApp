package app.submission.visitapp.login.repository

import app.submission.visitapp.login.dao.RoomDao
import app.submission.visitapp.login.models.Stores
import javax.inject.Inject

class StoreRepository @Inject constructor(private val storeDao: RoomDao) {
    suspend fun getRecords(): List<Stores>{
        return storeDao.getStores()
    }

    suspend fun insertRecords(stores: List<Stores>){
        storeDao.insertStores(stores)
    }

    suspend fun deleteRecords(){
        storeDao.delete()
    }
}