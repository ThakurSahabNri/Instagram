package com.example.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ReelDgBinding
import com.squareup.picasso.Picasso

class ReelsVpAdapter (var context: Context,var reelList:ArrayList<Reel>):RecyclerView.Adapter<ReelsVpAdapter.ViewHolder>(){

inner class ViewHolder(var binding: ReelDgBinding):
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding=ReelDgBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList[position].profileUrl).placeholder(R.drawable.profile).into(holder.binding.profileImage)
        holder.binding.tvDescription.setText(reelList[position].caption)
       holder.binding.videoView.setVideoPath(reelList[position].videoUrl)
        holder.binding.videoView.setOnPreparedListener{
            holder.binding.progressBar.visibility=View.GONE
           holder.binding.videoView.start()
        }

    }


}