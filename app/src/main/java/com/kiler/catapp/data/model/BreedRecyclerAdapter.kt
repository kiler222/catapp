package com.kiler.catapp.data.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kiler.catapp.R
import kotlinx.android.synthetic.main.layout_card_item.view.*

import kotlin.collections.ArrayList

class BreedRecyclerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Breed> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BreedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is BreedViewHolder ->{
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun submitList(breedList: List<Breed>){

        items = breedList
        notifyDataSetChanged()
    }

    class BreedViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {

            val breedImage = itemView.breed_image
            val breedName = itemView.breed_name
            val breedDescription = itemView.breed_description

            fun bind(breed: Breed){
                breedName.setText(breed.name)
                breedDescription.setText(breed.description)

                val requestOption = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .override(100, 100)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE)
                    .dontAnimate()

                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOption)
                    .load(breed.image)
                    .thumbnail(0.5f)
                    .into(breedImage)


            }
    }


}