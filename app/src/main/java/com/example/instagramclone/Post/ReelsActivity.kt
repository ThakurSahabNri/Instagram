package com.example.instagramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivityReelsBinding
import com.example.instagramclone.util.REEL
import com.example.instagramclone.util.REEL_FOLDER
import com.example.instagramclone.util.USER_NODE
import com.example.instagramclone.util.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {
    private val binding by lazy {
              ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
    private lateinit var progressDialog:ProgressDialog
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER,progressDialog) { url->
                if (url != null) {
                    videoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading . . .")


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            finish()
        }

        binding.selectReel.setOnClickListener{
            launcher.launch("video/*")
            progressDialog.show()
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }
        binding.btnUpload.setOnClickListener {
            if (::videoUrl.isInitialized) {
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener {
                        val user: User = it.toObject<User>()!!
                        val reel = Reel(
                            videoUrl,
                            binding.txtCaption.editText?.text.toString(),
                            user.image!!
                        )

                        Firebase.firestore.collection(REEL)
                            .document().set(reel).addOnSuccessListener {
                                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL)
                                    .document().set(reel).addOnSuccessListener {
                                        startActivity(
                                            Intent(
                                                this@ReelsActivity,
                                                HomeActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                            }

                    }
            }else{
                Toast.makeText(this@ReelsActivity,"Please select a video"
                ,Toast.LENGTH_SHORT).show()
            }

        }
    }
}
