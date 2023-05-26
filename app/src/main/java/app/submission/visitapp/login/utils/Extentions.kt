package app.submission.visitapp.login.utils

import android.app.AlertDialog
import android.content.Context

fun alertDialog(title: String, message: String, context: Context){
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)

    builder.setPositiveButton("Ok") { _, _ ->
    }
    builder.show()
}