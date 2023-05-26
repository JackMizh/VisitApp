package app.submission.visitapp.storelist.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.R
import app.submission.visitapp.databinding.ActivityStorelistBinding
import app.submission.visitapp.login.utils.Tools
import app.submission.visitapp.storedetail.presentation.StoredetailViewModel
import app.submission.visitapp.storelist.adapter.StoreAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class StorelistActivity : AppCompatActivity(), OnMapReadyCallback {

    private var _binding: ActivityStorelistBinding? = null
    private val binding get() = _binding!!
    private val storelistViewModel by viewModels<StorelistViewModel>()
    private val storedetailViewModel by viewModels<StoredetailViewModel>()
    private lateinit var storeAdapter: StoreAdapter

    private var isPermissionGranted = false
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLoc: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStorelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tools.setSystemBarColor(this, R.color.main)
        Tools.setSystemBarLight(this)

        checkpermission()

        with(binding){
            imgBack.setOnClickListener {
                finish()
            }

            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val current = LocalDateTime.now().format(formatter)
            tvTitleList.text = "List kunjungan $current"

            if(isPermissionGranted) {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this@StorelistActivity)
                mapView.getMapAsync(this@StorelistActivity)
                mapView.onCreate(savedInstanceState)
            }
        }
    }

    private fun checkpermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0!!.areAllPermissionsGranted()) {
                        isPermissionGranted = true
                        Toast.makeText(this@StorelistActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }

                    if (p0.isAnyPermissionPermanentlyDenied) {
                        val intent = Intent()
                        val uri = Uri.fromParts("package", packageName, "")
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.data = uri
                        startActivity(intent)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1!!.continuePermissionRequest()
                }
            }).check()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {

        fusedLocationClient.lastLocation.addOnCompleteListener {
            if(it.isSuccessful){
                it.result
                mMap = p0
                mMap!!.isMyLocationEnabled = true
                mMap!!.uiSettings.isMyLocationButtonEnabled = true
                mMap!!.setOnMyLocationChangeListener(myLocationChangeListener)
                storelistViewModel.viewModelScope.launch {
                    for(i in storelistViewModel.getdata()){
                        mMap!!.addMarker(
                            MarkerOptions()
                                .position(
                                    LatLng(
                                        i.latitude.toDouble(),
                                        i.longitude.toDouble()
                                    )
                                )
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        )
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        this.recreate()
    }

    override fun onStart() {
        super.onStart()
        with(binding){
            mapView.onStart()
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding){
            mapView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding){
            mapView.onPause()
        }
    }

    override fun onStop() {
        super.onStop()
        with(binding){
            mapView.onStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        with(binding){
            mapView.onDestroy()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        with(binding){
            mapView.onSaveInstanceState(outState)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        with(binding){
            mapView.onLowMemory()
        }
    }

    private val myLocationChangeListener =
        OnMyLocationChangeListener { location ->
            val loc = LatLng(location.latitude, location.longitude)
            if (mMap != null) {
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
            }
            val current = Location("Current")
            current.latitude = location.latitude
            current.longitude = location.longitude
            currentLoc = current

            with(binding){
                rv.apply {
                    storelistViewModel.viewModelScope.launch {
                        storeAdapter = StoreAdapter(storelistViewModel.getdata(), currentLoc!!, storedetailViewModel.getdata())
                        adapter = storeAdapter
                    }
                }
            }
        }
}