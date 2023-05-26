package app.submission.visitapp.login.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.submission.visitapp.login.dao.RoomDao
import app.submission.visitapp.login.models.Stores


@Database(entities = [Stores::class], version = 1)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun storesDao() : RoomDao

    companion object{
        private var INSTANCE : StoreDatabase? = null

        fun getDatabase(context: Context): StoreDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    StoreDatabase::class.java,
                    "storesDB"
                ).build()
            }

            return INSTANCE!!
        }
    }

}