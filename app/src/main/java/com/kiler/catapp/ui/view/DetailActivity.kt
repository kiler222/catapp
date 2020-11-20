package com.kiler.catapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kiler.catapp.R
import com.kiler.catapp.ui.main.CatViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var catViewModel: CatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val temperament = intent.getStringExtra("temperament") ?: "N/A"
        val code = intent.getStringExtra("code") ?: "N/A"
        val wikipediaUrl = intent.getStringExtra("wikipediaUrl") ?: ""
        val name = intent.getStringExtra("name") ?: "N/A"
        val description = intent.getStringExtra("description") ?: "N/A"
        val image = intent.getStringExtra("image") ?: "N/A"

        Log.e("detail", "pos = $temperament\n$code\n$wikipediaUrl\n$name\n$description")


        this.temperament.text = temperament
        this.code.text = code
        this.name.text = name
        this.description.text = description
        this.wiki.text = wikipediaUrl



        val requestOption = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
//            .override(100, 100)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.IMMEDIATE)
            .dontAnimate()

        Glide.with(this)
            .applyDefaultRequestOptions(requestOption)
            .load(image)
//            .thumbnail(0.5f)
            .into(imageView)


    }
}