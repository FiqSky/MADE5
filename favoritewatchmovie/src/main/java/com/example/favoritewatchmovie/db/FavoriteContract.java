package com.example.favoritewatchmovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {
    public static final String AUTHORITY = "com.farzain.watchmovie";
    public static final String SCHEME = "content";

    public static final String TABLE_MOVIE = "Movie";
    public static final String TABLE_SERIES = "Series";

    public static final class MovieColumns implements BaseColumns {
        //        public static final String ID_MOVIE = "id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELESE_DATE = "release_date";
    }

    public static final class SeriesColumns implements BaseColumns {
        //        public static final String ID_SERIES = "id";
        public static final String TITLE_SERIES = "title";
        public static final String OVERVIEW_SERIES = "overview";
        public static final String POSTER_PATH_SERIES = "poster_path";
        public static final String RELESE_DATE_SERIES = "release_date";
    }

    // untuk membuat URI content://com.farzain.watchmovie
    public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static final Uri CONTENT_URI_SERIES = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_SERIES)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    /*public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }*/

    /*public static final class FavoriteEntry implements BaseColumns {

        public static final String TABLE_NAME = "Movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }*/

}
