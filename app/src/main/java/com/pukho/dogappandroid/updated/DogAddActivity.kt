package com.pukho.dogappandroid.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.pukho.dogappandroid.model.Dog
import com.pukho.dogappandroid.server.DogService
import com.pukho.dogappandroid.R
import org.jetbrains.anko.*

/**
 * Created by Pukho on 19.03.2017.
 */
class DogAddActivity : EditDogsSetActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.baseView!!.find<ImageView>(R.id.dogImageEdit).imageResource = R.drawable.dog_default
        val textNameView : TextView = super.baseView!!.find<EditText>(R.id.dogNameEdit);

        super.baseView!!.find<FloatingActionButton>(R.id.floatBttn).onClick {
            if (!textNameView.text.isEmpty()) {
               val dog : Dog = Dog(textNameView.text.toString())
               createDog(dog);
            }
            else {
                toast("enter a name!")
            }
        }
    }

    private fun createDog(dog: Dog) {
        doAsync {
            val createdDog = DogService.api
                    .createDog(dog, super.createFileFromImage()).execute().body();
            uiThread {
                finishActivity(createdDog)
            }
        }
    }

}