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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        catViewModel = ViewModelProvider(this).get(CatViewModel::class.java)
        initRecyclerView()
        setSubscribtions()

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

    private fun setSubscribtions() {
        subscribe(catViewModel.getBreeds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getImages(it.breeds)
            }, {
                Log.e("PJ", "error = ${it.localizedMessage}")
            }))
    }


    private fun getImages(breedList: List<Breed>){
        breedOnView = breedList
        breedOnView.forEach {breed ->
            subscribe(catViewModel.getImage(breed.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    breed.image = it.url
                    breedAdapter.submitList(breedOnView)
                    progressBar.visibility = View.GONE
                }, {
                    Log.e(TAG, "error = ${it.localizedMessage}")
                }))
        }
    }

    private fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
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


}
