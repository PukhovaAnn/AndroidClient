package com.pukho.dogappandroid.ui

import android.support.design.widget.FloatingActionButton
import android.view.Gravity
import android.view.ViewManager
import android.widget.ImageView
import com.pukho.dogappandroid.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.design.coordinatorLayout

/**
 * Created by Pukho on 18.03.2017.
 */
class DogUpdatedUI<T> : AnkoComponent<T> {

    inline fun ViewManager.viewFloatBttn(theme: Int = R.style.FloatBttn,
                                         init: FloatingActionButton.() -> Unit)
            = ankoView(::FloatingActionButton, theme, init)

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        coordinatorLayout() {

            relativeLayout {
                imageView {
                    id = R.id.dogImageEdit
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(width = matchParent, height = dip(300)) {
                    alignParentStart()
                }
                editText() {
                    id = R.id.dogNameEdit
                }.lparams {
                    alignParentLeft()
                    below(R.id.dogImageEdit)
                }
            }

            viewFloatBttn() {
                id = R.id.floatBttn
                imageResource = android.R.drawable.ic_menu_add
            }.lparams {
                anchorId = R.id.dogImageEdit
                marginEnd = dip(20)
                anchorGravity = Gravity.BOTTOM or Gravity.END
            }
        }

    }
}