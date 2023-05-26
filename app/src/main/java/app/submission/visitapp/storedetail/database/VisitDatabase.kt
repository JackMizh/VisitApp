package app.submission.visitapp.storedetail.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.submission.visitapp.storedetail.dao.VisitDao
import app.submission.visitapp.storedetail.models.Visit

@Database(entities = [Visit::class], version = 1)
abstract class VisitDatabase : RoomDatabase() {

    abstract fun visitdao() : VisitDao

    companion object{
        private var INSTANCE : VisitDatabase? = null

        fun getDatabase(context: Context): VisitDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    VisitDatabase::class.java,
                    "visitDB"
                ).build()
            }

            return INSTANCE!!
        }
    }

}