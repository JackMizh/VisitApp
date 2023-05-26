package app.submission.visitapp.storelist.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.submission.visitapp.databinding.ItemStoreBinding
import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.storedetail.models.Visit
import app.submission.visitapp.storedetail.presentation.StoredetailActivity
import com.google.android.gms.maps.model.LatLng
import java.text.DecimalFormat


class StoreAdapter(private val storeList: List<Stores>, private val currentLoc: Location,
private val visitList: List<Visit>): RecyclerView.Adapter<StoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder =
        StoreViewHolder(ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(storeList[position], currentLoc, visitList)
    }

    override fun getItemCount(): Int = storeList.size
}

class StoreViewHolder(private val binding: ItemStoreBinding): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(stores: Stores, currentLoc: Location, visitList: List<Visit>) {
        with(binding) {
            tvStoreName.text = stores.store_name
            tvStoreCluster.text = "Cluster : Small"
            tvStoreRange.text = "TT Regular - IRTM Big Store"

            val markerLoc = Location("Marker")
            markerLoc.latitude = stores.latitude.toDouble()
            markerLoc.longitude = stores.longitude.toDouble()
            val current = Location("Current")
            current.latitude = currentLoc.latitude
            current.longitude = currentLoc.longitude
            tvStoreRemaining.text = CalculationByDistance(LatLng(markerLoc.latitude, markerLoc.longitude), LatLng(currentLoc.latitude, currentLoc.longitude)).toString()

            for(i in visitList){
                if(i.store_id == stores.id){
                    tvPerfectStore.visibility = View.VISIBLE
                }
            }

            cardStore.setOnClickListener {
                if(tvPerfectStore.visibility == View.GONE){
                    val intent = Intent(binding.root.context, StoredetailActivity::class.java)
                    intent.putExtra("store", stores)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }

    fun CalculationByDistance(StartP: LatLng, EndP: LatLng): String {
        val Radius = 6371 // radius of earth in Km
        val lat1 = StartP.latitude
        val lat2 = EndP.latitude
        val lon1 = StartP.longitude
        val lon2 = EndP.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + (Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2)))
        val c = 2 * Math.asin(Math.sqrt(a))
        val valueResult = Radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))

        return if(kmInDec == 0){
            "${meterInDec}m"
        }else{
            "${kmInDec}.${meterInDec}km"
        }
    }
}