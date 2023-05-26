package app.submission.visitapp.storedetail.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visit")
data class Visit (
    @PrimaryKey()
    val store_id: Int,
    val visit_time: String
) : java.io.Serializable