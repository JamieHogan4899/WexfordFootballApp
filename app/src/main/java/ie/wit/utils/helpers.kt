package ie.wit.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import ie.wit.R
import ie.wit.main.FootballApp
import java.io.ByteArrayOutputStream

fun createLoader(activity: FragmentActivity) : AlertDialog {
    val loaderBuilder = AlertDialog.Builder(activity)
        .setCancelable(true) // 'false' if you want user to wait
        .setView(R.layout.loading)
    var loader = loaderBuilder.create()
    loader.setTitle(R.string.app_name)
    loader.setIcon(R.mipmap.ic_nav_screen_five_foreground)

    return loader
}

fun showLoader(loader: AlertDialog, message: String) {
    if (!loader.isShowing()) {
        loader.setTitle(message)
        loader.show()
    }
}

fun hideLoader(loader: AlertDialog) {
    if (loader.isShowing())
        loader.dismiss()
}

fun uploadImageView(app: FootballApp , imageView: ImageView) {
    // Get the data from an ImageView as bytes
    val uid = app.auth.currentUser!!.uid
    val imageRef = app.storage.child("photos").child("${uid}.jpg")
    val bitmap = (imageView.drawable as BitmapDrawable).bitmap
    val baos = ByteArrayOutputStream()

    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val data = baos.toByteArray()

    var uploadTask = imageRef.putBytes(data)
}



