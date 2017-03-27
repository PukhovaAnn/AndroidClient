package com.pukho.dogappandroid.ui

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.ViewManager
import com.pukho.dogappandroid.adapter.DogAdapter
import com.pukho.dogappandroid.activity.MainActivity
import com.pukho.dogappandroid.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by Pukho on 18.03.2017.
 */
class DogRecycleViewUI(val dogAdapter : DogAdapter) : AnkoComponent<MainActivity> {

    inline fun ViewManager.viewFloatBttn(theme: Int = R.style.Base_ThemeOverlay_AppCompat_Light,
                                         init: FloatingActionButton.() -> Unit)
            = ankoView(::FloatingActionButton, theme, init)

        override fun createView(ui: AnkoContext<MainActivity>): ViewGroup {
            with(ui) {

                return relativeLayout {
                    recyclerView {
                        val orientation = LinearLayoutManager.VERTICAL
                        layoutManager = LinearLayoutManager(context, orientation, true)
                        adapter = dogAdapter
                    }.lparams(width = matchParent, height = matchParent) {
                        alignParentTop()
                    }

                    viewFloatBttn() {
                        id = R.id.floatAddDogBttn
                        imageResource = android.R.drawable.ic_menu_add
                    }.lparams {
                        alignParentEnd()
                        alignParentBottom()
                        margin = dip(10)

                    }
                }
            }
        }
}