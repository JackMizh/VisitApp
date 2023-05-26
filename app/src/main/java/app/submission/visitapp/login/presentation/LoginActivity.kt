package app.submission.visitapp.login.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.submission.visitapp.R
import app.submission.visitapp.databinding.ActivityLoginBinding
import app.submission.visitapp.login.utils.Tools
import app.submission.visitapp.login.utils.alertDialog
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tools.setSystemBarColor(this, R.color.main)
        Tools.setSystemBarLight(this)

        with(binding){
            btnLogin.setOnClickListener {
                if(etUsername.text.toString() == "" || etPassword.text.toString() == ""){
                    alertDialog(
                        "Form Invalid",
                        "Please fill form below completely, and try again",
                        binding.root.context
                    )
                }else{
                    Log.d("input", etPassword.text.toString() + etUsername.text.toString())
                    loginViewModel.login(
                        username = etUsername.text.toString()
                            .toRequestBody("text/plain".toMediaTypeOrNull()),
                        password = etPassword.text.toString()
                            .toRequestBody("text/plain".toMediaTypeOrNull()),
                        this@LoginActivity
                    )
                }
            }
        }
    }
}