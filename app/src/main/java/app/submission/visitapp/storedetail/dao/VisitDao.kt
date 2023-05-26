package app.submission.visitapp.storedetail.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.submission.visitapp.storedetail.models.Visit

@Dao
interface VisitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVisit(stores: Visit)

    @Query("SELECT * FROM visit")
    suspend fun getVisit() : List<Visit>

    @Query("DELETE FROM visit")
    suspend fun delete()

}