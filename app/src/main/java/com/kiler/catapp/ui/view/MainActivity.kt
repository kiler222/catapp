package com.kiler.catapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiler.catapp.R.*
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.model.BreedRecyclerAdapter
import com.kiler.catapp.ui.base.TopSpacingDecoration
import com.kiler.catapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "PJMainCatActivity"

    @Inject lateinit var viewModel:  MainViewModel
    private lateinit var breedAdapter: BreedRecyclerAdapter
    private lateinit var breedOnView: List<Breed>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        initRecyclerView()
        Log.e(TAG, "onCreate")
        setupObservers()


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
            breedAdapter = BreedRecyclerAdapter()
            adapter = breedAdapter


        }
    }



    private fun setupObservers() {

        viewModel.mutableLiveBreeds.observe(this, {

            Log.e(TAG, "breedObserver")
            progressBar.visibility = View.GONE
            progressBar2.visibility = View.GONE
            breedAdapter.submitList(it)
            breedOnView = it
        })


        viewModel.downloadStatus.observe(this, {

            progressBar2.progress = it

        })



    }



}
