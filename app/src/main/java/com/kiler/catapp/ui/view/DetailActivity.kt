package com.kiler.catapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kiler.catapp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //getting data from the intent and passing them to the coresponding fields in detail avticity
        val temperament = intent.getStringExtra("temperament") ?: "N/A"
        val code = intent.getStringExtra("code") ?: "N/A"
        val wikipediaUrl = intent.getStringExtra("wikipediaUrl") ?: ""
        val name = intent.getStringExtra("name") ?: "N/A"
        val description = intent.getStringExtra("description") ?: "N/A"
        val image = intent.getStringExtra("image") ?: "N/A"


        this.temperament.text = temperament
        this.code.text = code
        this.name.text = name
        this.description.text = description
        this.wiki.text = wikipediaUrl



        val requestOption = RequestOptions()
            .placeholder(R.drawable.ic_img_placeholder)
            .error(R.drawable.ic_img_error)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.IMMEDIATE)
            .dontAnimate()

        Glide.with(this)
            .applyDefaultRequestOptions(requestOption)
            .load(image)
            .into(imageView)


    }
}