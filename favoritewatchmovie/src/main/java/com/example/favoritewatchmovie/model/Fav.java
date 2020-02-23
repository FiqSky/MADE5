package com.example.favoritewatchmovie.model;

public class Fav {
    private String title;
    private String overview;
    private String poster_path;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }


    public Fav(String title,String overview,String relese,String poster_path){
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
    }
}
