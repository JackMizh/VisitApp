package app.submission.visitapp.storedetail.repository

import app.submission.visitapp.login.dao.RoomDao
import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.storedetail.dao.VisitDao
import app.submission.visitapp.storedetail.models.Visit
import javax.inject.Inject

class VisitRepository @Inject constructor(private val visitDao: VisitDao) {
    suspend fun getRecords(): List<Visit>{
        return visitDao.getVisit()
    }

    suspend fun insertRecords(visit: Visit){
        visitDao.insertVisit(visit)
    }

    suspend fun deleteRecords(){
        visitDao.delete()
    }
}