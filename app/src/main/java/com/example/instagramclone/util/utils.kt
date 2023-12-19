package com.example.instagramclone.util

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri

import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri,folderName:String,callback:(String?)->Unit){
    var imageUrl:String?=null
   
   FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID()
       .toString()).putFile(uri).addOnSuccessListener {
       it.storage.downloadUrl.addOnSuccessListener {
           imageUrl=it.toString()
           callback(imageUrl)
       }
   }
}

@SuppressLint("SuspiciousIndentation")
fun uploadVideo(uri: Uri, folderName:String, progressDialog:ProgressDialog, callback:(String?)->Unit){
    var videoUrl:String?=null

    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID()
        .toString()).putFile(uri).addOnSuccessListener {
        it.storage.downloadUrl.addOnSuccessListener {
            videoUrl=it.toString()
            progressDialog.dismiss()
            callback(videoUrl)
        }
    }
        .addOnProgressListener {
            val uploadValue:Long=(it.bytesTransferred/it.totalByteCount)*100

            progressDialog.setMessage("Uploading $uploadValue %" )
        }

}
