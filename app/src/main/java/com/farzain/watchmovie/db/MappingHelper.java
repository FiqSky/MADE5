package com.farzain.watchmovie.db;

import android.database.Cursor;

import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.Series;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.TITLE;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.RELESE_DATE;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.TITLE_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.OVERVIEW_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.RELESE_DATE_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.POSTER_PATH_SERIES;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayListMovie(Cursor itemCursor) {
        ArrayList<Movie> itemListMovie = new ArrayList<>();

        while (itemCursor.moveToNext()) {
            int id = itemCursor.getInt(itemCursor.getColumnIndexOrThrow(_ID));
            String title = itemCursor.getString(itemCursor.getColumnIndexOrThrow(TITLE));
            String overview = itemCursor.getString(itemCursor.getColumnIndexOrThrow(OVERVIEW));
            String relese = itemCursor.getString(itemCursor.getColumnIndexOrThrow(RELESE_DATE));
            String photo = itemCursor.getString(itemCursor.getColumnIndexOrThrow(POSTER_PATH));

            itemListMovie.add(new Movie(id, title,overview, relese, photo));
        }
        return itemListMovie;
    }

    public static ArrayList<Series> mapCursorToArrayListSeries(Cursor itemCursor) {
        ArrayList<Series> itemListSeries = new ArrayList<>();

        while (itemCursor.moveToNext()) {
            int id = itemCursor.getInt(itemCursor.getColumnIndexOrThrow(_ID));
            String title = itemCursor.getString(itemCursor.getColumnIndexOrThrow(TITLE_SERIES));
            String overview = itemCursor.getString(itemCursor.getColumnIndexOrThrow(OVERVIEW_SERIES));
            String relese = itemCursor.getString(itemCursor.getColumnIndexOrThrow(RELESE_DATE_SERIES));
            String photo = itemCursor.getString(itemCursor.getColumnIndexOrThrow(POSTER_PATH_SERIES));

            itemListSeries.add(new Series(id, title,overview, relese, photo));
        }
        return itemListSeries;
    }
}
