package com.example.imaktab.extension

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.imaktab.extension.Contstans.IMAGE_DIRECTORY
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

fun Activity.showPictureDialog() {


    val CAMERA = 2
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("select from gallery", "select from camera")
        pictureDialog.setItems(pictureDialogItems) { dialog, which ->
            when (which) {

                0 -> if (checkPersmissionGallery()) choosePhotoFromGallary() else requestPermissionGallery()
                1 -> if (checkPersmission()) takePhotoFromCamera() else requestPermission()
            }
        }
        pictureDialog.show()
    }
 fun Context.checkPersmissionGallery(): Boolean {
    return (ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ) ==
            PackageManager.PERMISSION_GRANTED)
}    fun Activity.choosePhotoFromGallary() {
    val GALLERY = 1

    val galleryIntent = Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    )
    galleryIntent.type = "image/*"
    galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivityForResult(Intent.createChooser(galleryIntent, "image"), GALLERY)
}
 fun Activity.requestPermissionGallery() {
    val PERMISSION_REQUEST_CODE_GALLERY: Int = 101
    ActivityCompat.requestPermissions(
       this,
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
        PERMISSION_REQUEST_CODE_GALLERY
    )
}
fun Activity.requestPermission() {
   val PERMISSION_REQUEST_CODE_CAMERA: Int = 102
    ActivityCompat.requestPermissions(
        this,
        arrayOf(android.Manifest.permission.CAMERA),
        PERMISSION_REQUEST_CODE_CAMERA
    )
}
 fun Activity.takePhotoFromCamera() {
     val CAMERA = 2

    val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intentCamera, CAMERA)
}
fun Activity.checkPersmission(): Boolean {
    return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED)
}
// fun Activity.showDateDialog() {
//    val c = Calendar.getInstance()
//    val year = c.get(Calendar.YEAR)
//    val month = c.get(Calendar.MONTH)
//    val day = c.get(Calendar.DAY_OF_MONTH)
//
//    val dpd = DatePickerDialog(this,
//        R.style.my_dialog_theme, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//            // Display Selected date in Toast
////            Toast.makeText(this, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
//
//        }, year, month, day)
//    dpd.show()
////        dt?.show()
//}
 fun Activity.saveImage(thumbnail: Bitmap): String {
     var imagePath: String = ""
    val bytes = ByteArrayOutputStream()
    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
    val wallpaperDirectory = File(
        (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
    )
    // have the object build the directory structure, if needed.
    Log.d("fee", wallpaperDirectory.toString())
    if (!wallpaperDirectory.exists()) {

        wallpaperDirectory.mkdirs()
    }

    try {
        Log.d("heel", wallpaperDirectory.toString())
        val f = File(
            wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg")
        )
        f.createNewFile()
        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        MediaScannerConnection.scanFile(
            this,
            arrayOf(f.path),
            arrayOf("image/jpeg"), null
        )
        fo.close()
        Log.d("TAG", "File Saved::--->" + f.absolutePath)

        imagePath = f.absolutePath
        return f.absolutePath
    } catch (e1: IOException) {
        e1.printStackTrace()
    }

    return " "
}

fun getRealPathFromURI(context: Context, contentURI: Uri): String? {
    //Log.e("in","conversion"+contentURI.getPath());
    var path: String?
    val cursor: Cursor? = context.contentResolver
        .query(contentURI, null, null, null, null)
    if (cursor == null)
        path = contentURI.path
    else {
        cursor.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        path = cursor.getString(idx)
    }
    if (cursor != null)
        cursor.close()
    Log.e("GGGG CCC ", path)
    return path
}



