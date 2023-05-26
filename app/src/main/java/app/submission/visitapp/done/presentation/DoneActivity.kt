package app.submission.visitapp.done.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.submission.visitapp.R
import app.submission.visitapp.databinding.ActivityDoneBinding
import app.submission.visitapp.login.models.Stores
import app.submission.visitapp.login.utils.Tools
import java.io.FileDescriptor
import java.io.IOException

class DoneActivity : AppCompatActivity() {

    private var _binding: ActivityDoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tools.setSystemBarColor(this, R.color.main)
        Tools.setSystemBarLight(this)

        val store = intent.getSerializableExtra("store") as? Stores
        val image = Uri.parse(intent.getStringExtra("image"))
        val inputImage = uriToBitmap(image)

        with(binding){
            imgSwafoto.setImageBitmap(inputImage)
            tvStoreCode.text = store!!.store_code
            tvStoreName.text = store.store_name

            btnSelesai.setOnClickListener {
                finish()
            }
        }
    }

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
}