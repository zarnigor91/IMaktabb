package com.example.imaktab.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.imaktab.R
import com.example.imaktab.about_app.AboutAppFragment
import com.example.imaktab.settings.SettingsFragment
import com.example.imaktab.extension.*
import kotlinx.android.synthetic.main.profile_layout.*
import java.io.IOException

class ProfileFragment:Fragment(R.layout.profile_layout){
    private val GALLERY = 1
    private val CAMERA = 2
    private var imagePath: String = ""
    private val PERMISSION_REQUEST_CODE_GALLERY: Int = 101
    private val PERMISSION_REQUEST_CODE_CAMERA: Int = 102
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        relat_settings.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,
                SettingsFragment()
            )?.commit()

        }
        im_photoporat.setOnClickListener {
            activity!!.showPictureDialog()
            Toast.makeText(context,"fgfd",Toast.LENGTH_SHORT)
        }
        tv_about_app.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,
                AboutAppFragment()
            )?.commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                var contentURI = data.data
                try {
                    var bitmap: Bitmap
                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()

                    if (Build.VERSION.SDK_INT >= 29) {
                        bitmap = ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(
                                activity!!.contentResolver,
                                contentURI!!
                            )
                        )
                    } else {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            activity!!.contentResolver,
                            contentURI!!
                        )
                    }


                    imagePath = getRealPathFromURI(context!!, contentURI)!!
                    imag_photo!!.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                }


            }
        } else
            if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
                val thumbnail = data!!.extras!!.get("data") as Bitmap
                imag_photo!!.setImageBitmap(thumbnail)

                activity!!.saveImage(thumbnail)
                Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()

            }
    }
    override fun onStart() {
        super.onStart()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    activity!!.takePhotoFromCamera()

                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            PERMISSION_REQUEST_CODE_GALLERY -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    activity!!.choosePhotoFromGallary()

                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}