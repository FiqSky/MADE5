package com.farzain.watchmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.Series;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.RELESE_DATE;
import static com.farzain.watchmovie.db.DatabaseContract.MovieColumns.TITLE;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.OVERVIEW_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.POSTER_PATH_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.RELESE_DATE_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.SeriesColumns.TITLE_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.TABLE_MOVIE;
import static com.farzain.watchmovie.db.DatabaseContract.TABLE_SERIES;

public class FavoriteHelper {
    private static final String DATABASE_MOVIE = TABLE_MOVIE;
    private static final String DATABASE_SERIES = TABLE_SERIES;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        Log.d(TAG, "open: " + database.isOpen());
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
        Log.d(TAG, "close: " + database.isOpen());
    }

    public ArrayList<Movie> getAllFavoriteMovie() {
        ArrayList<Movie> favoriteMovieArrayList = new ArrayList<>();

        Cursor cursor = database.query(DATABASE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setSynopsis(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELESE_DATE)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                favoriteMovieArrayList.add(movie);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return favoriteMovieArrayList;
    }

    public long insertMovie(Movie movie) {
        Log.d(TAG, "insertMovie: " + movie.getName() + movie.getSynopsis() + movie.getRelease() + movie.getPhoto());
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, movie.getId());
        contentValues.put(TITLE, movie.getName());
        contentValues.put(OVERVIEW, movie.getSynopsis());
        contentValues.put(RELESE_DATE, movie.getRelease());
        contentValues.put(POSTER_PATH, movie.getPhoto());
        Log.d(TAG, "insertMovie: " + contentValues);
        return database.insert(DATABASE_MOVIE, null, contentValues);
    }

    public int deleteMovie(int id) {
        return database.delete(DATABASE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public boolean checkMovie(int id) {
        String query = "SELECT * FROM " + DATABASE_MOVIE + " WHERE " + _ID + " =?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        boolean exist = false;
        if (cursor.moveToFirst()) {
            exist = true;
        }
        cursor.close();
        return exist;
    }

    public ArrayList<Series> getAllFavoriteSeries() {
        ArrayList<Series> favoriteSeriesArrayList = new ArrayList<>();

        Cursor cursor = database.query(DATABASE_SERIES,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Series series;
        if (cursor.getCount() > 0) {
            do {
                series = new Series();
                series.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                series.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_SERIES)));
                series.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELESE_DATE_SERIES)));
                series.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH_SERIES)));
                series.setSynopsis(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_SERIES)));
                favoriteSeriesArrayList.add(series);
                cursor.moveToNext();

            }
            while (!cursor.isAfterLast());
        }

        cursor.close();
        return favoriteSeriesArrayList;
    }

    public long insertSeries(Series series) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, series.getId());
        contentValues.put(TITLE_SERIES, series.getName());
        contentValues.put(OVERVIEW_SERIES, series.getSynopsis());
        contentValues.put(RELESE_DATE_SERIES, series.getRelease());
        contentValues.put(POSTER_PATH_SERIES, series.getPhoto());
        return database.insert(DATABASE_SERIES, null, contentValues);
    }

    public int deleteSeries(int id) {
        database.isOpen();
        return database.delete(DATABASE_SERIES, _ID + " = '" + id + "'", null);
    }

    public boolean checkSeries(int id) {
        String query = "SELECT * FROM " + DATABASE_SERIES + " WHERE " + _ID + " =?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        boolean exist = false;
        if (cursor.moveToFirst()) {
            exist = true;
        }
        cursor.close();
        return exist;
    }
}
