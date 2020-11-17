package com.kiler.catapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiler.catapp.ui.main.MainViewModel
import com.kiler.catapp.R.*
import com.kiler.catapp.data.api.ApiHelper
import com.kiler.catapp.data.api.RetrofitBuilder
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.model.BreedRecyclerAdapter
import com.kiler.catapp.ui.base.TopSpacingDecoration
import com.kiler.catapp.ui.base.ViewModelFactory
import com.kiler.catapp.utils.Status.LOADING
import com.kiler.catapp.utils.Status.SUCCESS
import com.kiler.catapp.utils.Status.ERROR
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val TAG = "PJMainCatActivity"

    private lateinit var viewModel: MainViewModel
    private lateinit var breedAdapter: BreedRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        initRecyclerView()
        Log.e(TAG, "onCreate")
        setupViewModel()
    //    setupUI()
        setupObservers()

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

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

//    private fun setupUI() {
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = MainAdapter(arrayListOf())
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        recyclerView.adapter = adapter
//    }

    private fun setupObservers() {
        viewModel.getBreeds().observe(this, Observer {
            it?.let { resource ->
//                Log.e(TAG, "xxxx ${resource.message}")
                when (resource.status) {
                    SUCCESS -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        resource.data?.let { breeds ->

                           updateBreedImage(breeds)

                        }


                    }
                    ERROR -> {
//                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        progressBar.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun updateBreedImage(breeds: List<Breed>){

   //     var newBreeds = breeds

        for (i in 0..breeds.size) {
            viewModel.getImage().observe(this,{

                it?.let { resource ->

                    when (resource.status) {
                        SUCCESS -> {
//
                            resource.data?.let { imageData ->
                                //                                            Log.e(TAG, "obrazek abys: ${imageData[0].url}")
                                val img = imageData[0].url
                                breeds[i].image = img
                                breedAdapter.submitList(breeds)
                                breedAdapter.notifyDataSetChanged()
                            }
                        }
                        ERROR -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        LOADING -> {
//                        progressBar.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
                        }



                    }
                }
            })
        }

        progressBar.visibility = View.GONE


    }


}
