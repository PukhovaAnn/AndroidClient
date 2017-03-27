package com.pukho.dogappandroid.adapter

import android.support.design.widget.FloatingActionButton
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ImageView
import com.pukho.dogappandroid.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

/**
 * Created by Pukho on 18.03.2017.
 */
class DogItemUI: AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): ViewGroup {
        with(ui) {
            val holder = relativeLayout(theme = R.style.AppTheme) {
                lparams(width = matchParent, height = dip(120)) {
                    backgroundResource = R.drawable.border
                }
                imageView {
                    id = R.id.dogImageRecycle
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(width = dip(100)) {
                    alignParentStart()
                    centerHorizontally()
                    margin = dip(10)
                }

                textView {
                    id = R.id.dogNameRecycle
                    textSize = resources.getDimension(R.dimen.text_size)
                }.lparams {
                    leftMargin = dip(15)
                    topMargin = dip(10)
                    rightOf(R.id.dogImageRecycle)
                }

                button("update") {
                    id = R.id.updateDogBttn
                }.lparams {
                    leftMargin = dip(10)
                    below(R.id.dogNameRecycle)
                    rightOf(R.id.dogImageRecycle)
                }
                button("delete", theme = R.style.BttnDeleteTheme) {
                    id = R.id.deleteDogBttn
                }.lparams {
                    below(R.id.dogNameRecycle)
                    rightOf(R.id.updateDogBttn)
                }
            }
            return holder;
        }
    }
}