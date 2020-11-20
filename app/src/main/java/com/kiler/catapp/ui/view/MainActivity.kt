package com.kiler.catapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiler.catapp.R.*
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.model.BreedRecyclerAdapter
import com.kiler.catapp.ui.base.TopSpacingDecoration
import com.kiler.catapp.ui.main.CatViewModel
//import com.kiler.catapp.ui.main.CatViewModel
//import com.kiler.catapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BreedRecyclerAdapter.OnItemClickListener {

    private val TAG = "PJMainActivity"
    private lateinit var catViewModel: CatViewModel
    private lateinit var breedAdapter: BreedRecyclerAdapter
    private lateinit var breedOnView: List<Breed>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        catViewModel = ViewModelProvider(this).get(CatViewModel::class.java)
        catViewModel.fetchBreeds()

        initRecyclerView()
        setupCatObservers()


        searchBreed.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                val userInput = newText.toLowerCase(Locale.ROOT)
                val findBreeds  = ArrayList<Breed>()

                breedOnView.forEach{

                    if (it.country.toLowerCase(Locale.ROOT).contains(userInput))
                        findBreeds.add(it)
                }

                breedAdapter.submitList(findBreeds)

                return true

            }

        })


    }



    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecorator = TopSpacingDecoration(30)
            addItemDecoration(topSpacingDecorator)
            breedAdapter = BreedRecyclerAdapter(this@MainActivity)
            adapter = breedAdapter


        }
    }

    override fun onItemClick(position: Int) {
        Log.e(TAG, "Klikniety $position kot. ${breedOnView[position].country}")
//        Toast.makeText(this, "Klikniety $position kot. ${breedOnView[position].country}", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("position", position)


        startActivity(intent)


    }



    private fun setupCatObservers() {
        Log.e(TAG, "przed breedObserver")

        catViewModel.mutableLiveBreeds.observe(this){

            Log.e(TAG, "breedObserverMutable")
            progressBar.visibility = View.GONE
            breedAdapter.submitList(it)
            breedOnView = it
        }

    }


}
