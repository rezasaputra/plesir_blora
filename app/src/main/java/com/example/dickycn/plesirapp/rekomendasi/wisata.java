package com.example.dickycn.plesirapp.rekomendasi;

/**
 * Created by rejak on 8/22/2017.
 */

public class wisata {
    private String title, thumbnailUrl;
    public wisata(){

    }
    public wisata (String name, String thumbnailUrl){
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String name){
        this.title = name;
    }
    public String getThumbnailUrl(){
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }
}

