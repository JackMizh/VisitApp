package app.submission.visitapp.login.dao

import androidx.room.*
import app.submission.visitapp.login.models.Stores

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStores(stores: List<Stores>)

    @Query("SELECT * FROM stores")
    suspend fun getStores() : List<Stores>

    @Query("DELETE FROM stores")
    suspend fun delete()

}