package com.pukho.dogappandroid.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.SpannableStringBuilder
import android.widget.EditText
import android.widget.ImageView
import com.pukho.dogappandroid.model.Dog
import com.pukho.dogappandroid.server.DogService
import com.pukho.dogappandroid.R
import org.jetbrains.anko.*
import java.io.InputStream

/**
 * Created by Pukho on 18.03.2017.
 */
class DogEditActivity : EditDogsSetActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog: Dog = intent.extras.getParcelable("dog")
        val textNameView = super.baseView!!.find<EditText>(R.id.dogNameEdit);
        val pictureView : ImageView = super.baseView!!.find<ImageView>(R.id.dogImageEdit);

        doAsync() {
            val ins: InputStream = DogService.api.downloadFileUri(dog.id)
                    .execute().body().byteStream();
            uiThread {
                pictureView.imageBitmap = BitmapFactory.decodeStream(ins);
            }
        }

        textNameView.text = SpannableStringBuilder(dog.name);

        super.baseView!!.find<FloatingActionButton>(R.id.floatBttn).onClick {
            if (!textNameView.text.isEmpty()) {
                dog.name = textNameView.text.toString()
                updateDog(dog);
            }
            else {
               toast("enter a name!")
            }
        }
    }

    fun updateDog(dog : Dog) {
        doAsync {
            val updatedDog = DogService.api
                    .uploadDog(dog.id, dog, super.createFileFromImage()).execute().body();
            uiThread {
                finishActivity(updatedDog)
            }
        }
    }
}