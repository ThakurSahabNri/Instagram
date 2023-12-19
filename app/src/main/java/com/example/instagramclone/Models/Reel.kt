package com.example.instagramclone.Models

class Reel {
    var videoUrl:String=""
    var caption:String=""
    var profileUrl:String?=null

  constructor( videoUrl:String, caption:String)  {
      this.videoUrl=videoUrl
      this.caption=caption
  }
    constructor()

    constructor( videoUrl:String, caption:String,profileImage:String)  {
        this.videoUrl=videoUrl
        this.caption=caption
        this.profileUrl=profileImage
    }

}