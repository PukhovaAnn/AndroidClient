package com.pukho.dogappandroid.adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.pukho.dogappandroid.R
import com.pukho.dogappandroid.adapter.RecyclerViewClickListener
import com.pukho.dogappandroid.model.Dog
import com.pukho.dogappandroid.server.DogService
import com.pukho.dogappandroid.adapter.DogItemUI
import org.jetbrains.anko.*
import java.io.InputStream


/**
 * Created by Pukho on 05.02.2017.
 */

class DogAdapter(val dogs: MutableList<Dog>) : RecyclerView.Adapter<DogAdapter.ViewHolder>() {

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    var onUpdateBttnListener : RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(DogItemUI().createView(AnkoContext.create(parent!!.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(dogs[position])

        doAsync() {
            val ins: InputStream = DogService.api.downloadFileUri(dogs[position].id)
                    .execute().body().byteStream();
            uiThread {
                holder!!.imageDogView.imageBitmap = BitmapFactory.decodeStream(ins);
            }
        }
    }

    public fun push(dog : Dog) {
        dogs.add(0, dog);
        notifyItemInserted(0)
    }

    public fun delete(dog : Dog) {
        val index = dogs.indexOf(dog)
        dogs.removeAt(index);
        notifyItemRemoved(index)
    }

    public fun upgrade(dog : Dog) {
        val index = dogs.indexOf(dog)
        dogs.set(index, dog);
        notifyItemChanged(index)
    }

override fun getItemCount(): Int {
return dogs.size
}

inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView),
        View.OnClickListener, AnkoLogger {
    override fun onClick(v: View) {
        onUpdateBttnListener!!.recyclerViewListClicked(v, this.layoutPosition)
    }

    val imageDogView = itemLayoutView.find<ImageView>(R.id.dogImageRecycle)
    val nameDogView : TextView = itemLayoutView.find<TextView>(R.id.dogNameRecycle)
    val deleteBttn : Button = itemLayoutView.find<Button>(R.id.deleteDogBttn)

    fun bind(dog : Dog) {
        nameDogView.text = dog.name
    }
    init {
        itemLayoutView.find<Button>(R.id.updateDogBttn).setOnClickListener(this)

        deleteBttn.onClick {
            doAsync {
                DogService.api
                        .deleteDog(dogs[layoutPosition].id).execute().body();
                uiThread {
                    delete(dogs[layoutPosition]);
                }
            }
        }
    }
}
}