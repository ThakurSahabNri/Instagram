package com.example.instagramclone.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.adapters.ReelsVpAdapter
import com.example.instagramclone.databinding.FragmentReelBinding
import com.example.instagramclone.util.REEL
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ReelFragment : Fragment() {
 lateinit var binding:FragmentReelBinding
 var reelslist=ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentReelBinding.inflate(inflater, container, false)



        val adapter= ReelsVpAdapter(requireContext(), reelslist)
//        binding.viewPager.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            val tempList= arrayListOf<Reel>()
            tempList.clear()
            for(i in it.documents){
                val reel: Reel =i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelslist.addAll(tempList)
            reelslist.reverse()
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}