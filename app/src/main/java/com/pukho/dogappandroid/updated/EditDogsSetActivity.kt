package com.pukho.dogappandroid.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.pukho.dogappandroid.model.Dog
import com.pukho.dogappandroid.R
import com.pukho.dogappandroid.ui.DogUpdatedUI
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.*
import java.io.File

/**
 * Created by Pukho on 19.03.2017.
 */
open class EditDogsSetActivity : Activity(), AnkoLogger {

    private val  RESULT_LOAD_IMG: Int = 202
    private val  MY_PERMISSIONS_REQUEST_READ_MEDIA: Int  = 125

    var filePath : String? = null

    var uri : Uri? = null
    var baseView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseView = DogUpdatedUI<EditDogsSetActivity>().setContentView(this)

        baseView!!.find<ImageView>(R.id.dogImageEdit).onClick {
            openGallery()
        }
    }

    fun checkPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_MEDIA)
        } else {

        }
    }

    fun createFileFromImage() : MultipartBody.Part? {
        if (filePath.isNullOrEmpty()) {
            return null;
        }
        val file: File = File(filePath);

        error(file.name);
        val type: MediaType = MediaType.parse(contentResolver.getType(uri));
        val requestFile : RequestBody
                = RequestBody.create(type, file)

        return MultipartBody.Part.createFormData("file", file.name,
                requestFile);
    }


    fun getFilePathByUri() {

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri,
                filePathColumn, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])

        filePath = cursor.getString(columnIndex)
    }

    fun finishActivity(dog: Dog) {
        val resultIntent = Intent()
        resultIntent.putExtra("dog", dog)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    fun openGallery() {
        checkPermission()

        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK
                    && null != data) {

                uri = data.data;
                toast(uri.toString())
                getFilePathByUri();
                find<ImageView>(R.id.dogImageEdit).setImageBitmap(BitmapFactory.decodeFile(filePath));
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            } else {
                toast("don't peek a picture")
            }
        } catch (e: Exception) {
           error(e.toString())
        }
    }
}