package com.example.instagramclone.Post


import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.Models.Post
import com.example.instagramclone.databinding.ActivityPostBinding
import com.example.instagramclone.util.POST
import com.example.instagramclone.util.POST_FOLDER
import com.example.instagramclone.util.USER_NODE
import com.example.instagramclone.util.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class PostActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }

    private var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                if (it != null) {
                    binding.image.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.image.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }
        binding.btnPost.setOnClickListener {
            if (imageUrl != null) {

                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener {
//      val user=it.toObject<User>()!!
                    val post: Post = Post(
                        imageUrl = imageUrl!!,
                        caption = binding.txtCaption.editText?.text.toString(),
                        uid = Firebase.auth.currentUser!!.uid,
                        time = System.currentTimeMillis().toString()
                    )

                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                            .set(post).addOnSuccessListener {
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                    }
                }

            }


        }
    }
}
