package com.farzain.watchmovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.TITLE;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.RELESE_DATE;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.farzain.watchmovie.db.DatabaseContract.getColumnInt;
import static com.farzain.watchmovie.db.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    private int id;
    private String name;
    private String synopsis;
    private String photo;
    private String release;

    protected Movie(Parcel in) {
        id = in.readInt();
        name = in.readString();
        synopsis = in.readString();
        photo = in.readString();
        release = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {
    }

    public Movie(int id,String name,String synopsis,String release, String photo) {
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
        this.release = release;
        this.photo = photo;
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor,_ID);
        this.name = getColumnString(cursor,TITLE);
        this.synopsis = getColumnString(cursor,OVERVIEW);
        this.release = getColumnString(cursor, RELESE_DATE);
        this.photo = getColumnString(cursor, POSTER_PATH);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(synopsis);
        dest.writeString(photo);
        dest.writeString(release);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
