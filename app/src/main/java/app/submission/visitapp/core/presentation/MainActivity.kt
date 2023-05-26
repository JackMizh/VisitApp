package app.submission.visitapp.core.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import app.submission.visitapp.R
import app.submission.visitapp.databinding.ActivityLoginBinding
import app.submission.visitapp.databinding.ActivityMainBinding
import app.submission.visitapp.login.presentation.LoginViewModel
import app.submission.visitapp.login.utils.Tools
import app.submission.visitapp.storelist.presentation.StorelistActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tools.setSystemBarColor(this, R.color.main)
        Tools.setSystemBarLight(this)


        with(binding){
            mainViewModel.viewModelScope.launch {
                val data = mainViewModel.getdata()
                val percentage = (data/data) * 100
                tvTotalStore.text = data.toString()
                tvActualStore.text = data.toString()
                tvPercentage.text = "$percentage%"
            }

            imgLogout.setOnClickListener {
                mainViewModel.logout(this@MainActivity)
            }

            imgVisit.setOnClickListener {
                startActivity(Intent(this@MainActivity, StorelistActivity::class.java))
            }
        }
    }
}