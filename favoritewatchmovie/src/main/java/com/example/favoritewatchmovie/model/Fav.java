package com.example.favoritewatchmovie.model;

public class Fav {
    private String title;
    private String overview;
    private String relese;
    private String poster_path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelese() {
        return relese;
    }

    public void setRelese(String relese) {
        this.relese = relese;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Fav(String title,String overview,String relese,String poster_path){
        this.title = title;
        this.overview = overview;
        this.relese = relese;
        this.poster_path = poster_path;
    }
}
