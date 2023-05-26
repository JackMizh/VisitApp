package app.submission.visitapp.login.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Stores (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val store_id: String,
    val store_code: String,
    val store_name: String,
    val address: String,
    val dc_id: String,
    val dc_name: String,
    val account_id: String,
    val account_name: String,
    val subchannel_id: String,
    val subchannel_name: String,
    val channel_id: String,
    val channel_name: String,
    val area_id: String,
    val area_name: String,
    val region_id: String,
    val region_name: String,
    val latitude: String,
    val longitude: String
) : java.io.Serializable