package com.example.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.databinding.MyReelsRvDesignBinding


class MyReelsRvAdapter(var context:Context,var reelslist: ArrayList<Reel>):RecyclerView.Adapter<MyReelsRvAdapter.ViewHolder>(){

    inner class ViewHolder(var binding:MyReelsRvDesignBinding):
            RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding= MyReelsRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelslist.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
   Glide.with(context).load(reelslist[position].videoUrl)
       .diskCacheStrategy(DiskCacheStrategy.ALL)
       .into(holder.binding.iv);

    }


}