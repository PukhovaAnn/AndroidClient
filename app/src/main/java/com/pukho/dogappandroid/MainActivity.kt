package com.pukho.dogappandroid.activity
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import com.pukho.dogappandroid.*
import com.pukho.dogappandroid.adapter.DogAdapter
import com.pukho.dogappandroid.adapter.RecyclerViewClickListener
import com.pukho.dogappandroid.model.Dog
import com.pukho.dogappandroid.server.DogService
import com.pukho.dogappandroid.ui.DogRecycleViewUI
import org.jetbrains.anko.*

class MainActivity : Activity() {

    val UPDATED_DOG : Int = 205
    val ADDED_DOG : Int = 207
    var dogAdapter : DogAdapter? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val dog : Dog?  = data?.getParcelableExtra("dog")

        if (requestCode == ADDED_DOG && resultCode == Activity.RESULT_OK) {
            dogAdapter!!.push(dog!!)
        }
        else if (requestCode == UPDATED_DOG && resultCode == Activity.RESULT_OK) {
            dogAdapter!!.upgrade(dog!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var dogs : MutableList<Dog>? = DownloadDogs().execute().get();

        if (dogs == null) dogs = mutableListOf();
        dogAdapter = DogAdapter(dogs)

        dogAdapter!!.onUpdateBttnListener = object : RecyclerViewClickListener {
            override fun recyclerViewListClicked(v: View, position: Int) {
                toast("editing dog with $position and id")
                startActivityForResult<DogEditActivity>(UPDATED_DOG, "dog" to dogs!![position]) ;
            }
        }

       DogRecycleViewUI(dogAdapter!!).setContentView(this);

        find<FloatingActionButton>(R.id.floatAddDogBttn).onClick {
            startActivityForResult<DogAddActivity>(ADDED_DOG)
        }
    }

    class DownloadDogs : AsyncTask<Void, Void, MutableList<Dog>?>() {
        override fun doInBackground(vararg params: Void?): MutableList<Dog>? {
            return DogService.api.getDogs().execute().body();
        }
    }
}
