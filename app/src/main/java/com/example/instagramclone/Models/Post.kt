package com.example.instagramclone.Models

class Post {
    var imageUrl:String=""
    var caption:String=""
    var uid:String=""
    var time:String=""

    constructor(imageUrl:String,caption:String){
        this.imageUrl=imageUrl
        this.caption=caption
    }
    constructor(imageUrl:String,caption:String,uid:String,time:String){
        this.imageUrl=imageUrl
        this.caption=caption
        this.uid=uid
        this.time=time
    }
    constructor()
}