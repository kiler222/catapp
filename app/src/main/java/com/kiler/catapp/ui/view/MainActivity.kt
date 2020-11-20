package com.kiler.catapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiler.catapp.R.*
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.ui.base.TopSpacingDecoration
import com.kiler.catapp.model.CatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BreedRecyclerAdapter.OnItemClickListener {

    private val TAG = "PJMainActivity"
    private lateinit var catViewModel: CatViewModel
    private lateinit var breedAdapter: BreedRecyclerAdapter
    private lateinit var breedOnView: List<Breed>
    private var findBreeds: ArrayList<Breed> = ArrayList()

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
                findBreeds.clear()
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

        val breed: Breed

        if (findBreeds.isNullOrEmpty()) breed = breedOnView[position] else breed = findBreeds[position]

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("temperament", breed.temperament)
        intent.putExtra("wikipediaUrl", breed.wikipediaUrl)
        intent.putExtra("code", breed.code)
        intent.putExtra("image", breed.image)
        intent.putExtra("name", breed.name)
        intent.putExtra("description", breed.description)

        startActivity(intent)

    }


    private fun setupCatObservers() {

        catViewModel.mutableLiveBreeds.observe(this){

            progressBar.visibility = View.GONE
            breedAdapter.submitList(it)
            breedOnView = it

        }
    }


}
