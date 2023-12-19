package com.example.instagramclone.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.adapters.MyReelsRvAdapter
import com.example.instagramclone.databinding.FragmentMyReelsBinding
import com.example.instagramclone.util.REEL
import com.example.instagramclone.util.REEL_FOLDER
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyReelsFragment : Fragment() {
    lateinit var binding:FragmentMyReelsBinding
    private var reelslist=ArrayList<Reel>()

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
        binding=FragmentMyReelsBinding.inflate(inflater, container, false)

        Firebase.firestore.collection(REEL_FOLDER).document(Firebase.auth.currentUser!!.uid).get()


        val adapter= MyReelsRvAdapter(requireContext(), reelslist)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+REEL).get().addOnSuccessListener {
            val tempList= arrayListOf<Reel>()
            reelslist.clear()
            for(i in it.documents){
                val reel:Reel =i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelslist.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}