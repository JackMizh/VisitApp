package app.submission.visitapp.storedetail.presentation

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.R
import app.submission.visitapp.databinding.ActivityStoredetailBinding
import app.submission.visitapp.done.presentation.DoneActivity
import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.login.presentation.LoginActivity
import app.submission.visitapp.login.utils.Tools
import app.submission.visitapp.login.utils.alertDialog
import app.submission.visitapp.storedetail.models.Visit
import app.submission.visitapp.storelist.adapter.StoreAdapter
import app.submission.visitapp.storelist.presentation.StorelistViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.FileDescriptor
import java.io.IOException
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@AndroidEntryPoint
class StoredetailActivity : AppCompatActivity() {

    private var _binding: ActivityStoredetailBinding? = null
    private val binding get() = _binding!!
    private var image_uri: Uri? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var isNear:Boolean = false
    private val storedetailViewModel by viewModels<StoredetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoredetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tools.setSystemBarColor(this, R.color.main)
        Tools.setSystemBarLight(this)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this@StoredetailActivity)
        val store = intent.getSerializableExtra("store") as? Stores
        with(binding){
            tvStoreName.text = store!!.store_name
            tvStoreAddress.text = store.address

            imgBack.setOnClickListener {
                this@StoredetailActivity.finish()
            }

            btnTrack.setOnClickListener {
                val geoUri =
                    "http://maps.google.com/maps?q=loc:" + store.latitude + "," + store.longitude + " (" + store.store_name + ")"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
                this@StoredetailActivity.startActivity(intent)
            }

            btnPhoto.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_DENIED
                    ) {
                        val permission = arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        requestPermissions(permission, 112)
                    } else {
                        openCamera()
                    }
                } else {
                    openCamera()
                }
            }

            btnCheck.setOnClickListener {
                fusedLocationClient.lastLocation.addOnCompleteListener(this@StoredetailActivity) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this@StoredetailActivity, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)!!
                        if(CalculationByDistance(LatLng(-6.3099425, 106.9485818), LatLng(store.latitude.toDouble(), store.longitude.toDouble()))){
                            imgLocation.setColorFilter(Color.parseColor("#3BE51E"), android.graphics.PorterDuff.Mode.SRC_IN)
                            tvLocationInformation.text = "Lokasi Sudah Sesuai"
                            tvLocationInformation.setTextColor(Color.parseColor("#3BE51E"))
                            btnReset.visibility = View.VISIBLE
                            btnReset.isEnabled = true
                            isNear = true
                        }else{
                            imgLocation.setColorFilter(Color.parseColor("#FFA430"), android.graphics.PorterDuff.Mode.SRC_IN)
                            tvLocationInformation.text = "Lokasi Belum Sesuai"
                            tvLocationInformation.setTextColor(Color.parseColor("#FFA430"))
                            btnReset.visibility = View.VISIBLE
                            btnReset.isEnabled = true
                            isNear = false
                        }
                    }
                }
            }

            btnReset.setOnClickListener {
                imgLocation.setColorFilter(Color.parseColor("#ff4444"), android.graphics.PorterDuff.Mode.SRC_IN)
                tvLocationInformation.text = "Lokasi Telah Direset"
                tvLocationInformation.setTextColor(Color.parseColor("#ff4444"))
                btnReset.visibility = View.INVISIBLE
                btnReset.isEnabled = false
                isNear = false
            }

            btnNovisit.setOnClickListener {
                this@StoredetailActivity.finish()
            }

            btnVisit.setOnClickListener {
                if(image_uri == null){
                    alertDialog(
                        "Swafoto Kosong",
                        "Silahkan swafoto terlebih dahulu untuk melakukan visit!",
                        this@StoredetailActivity
                    )
                }
                else{
                    if(isNear){
                        storedetailViewModel.viewModelScope.launch {
                            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                            val current = LocalDateTime.now().format(formatter)
                            storedetailViewModel.insert(Visit(store.id, current))
                            Handler(Looper.getMainLooper()).postDelayed({
                                val intent = Intent(this@StoredetailActivity, DoneActivity::class.java)
                                intent.putExtra("image", image_uri.toString())
                                intent.putExtra("store", store)
                                startActivity(intent)
                                finish()
                            }, 3000)
                        }
                    }
                    else {
                        alertDialog(
                            "Lokasi Tidak Sesuai",
                            "Jarak anda dengan lokasi lebih dari 100 meter, silahkan lebih dekat pada lokasi!",
                            this@StoredetailActivity
                        )
                    }
                }
            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        cameraActivityResultLauncher.launch(cameraIntent)
    }

    var cameraActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if (result.resultCode === RESULT_OK) {
            val inputImage = uriToBitmap(image_uri!!)
            with(binding){
                imgVisit.setImageBitmap(inputImage)
            }
        }
    }

    //TODO takes URI of the image and returns bitmap
    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun CalculationByDistance(StartP: LatLng, EndP: LatLng): Boolean {
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

        return kmInDec == 0 && meterInDec <= 100
    }
}