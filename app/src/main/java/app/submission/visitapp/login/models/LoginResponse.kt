package app.submission.visitapp.login.models

data class LoginResponse (
    val status: String,
    val message: String,
    val stores: ArrayList<Stores>
)