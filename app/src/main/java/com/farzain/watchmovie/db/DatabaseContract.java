package com.farzain.watchmovie.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_MOVIE = "Movie";
    public static final String TABLE_SERIES = "Series";

    public static final class MovieColumns implements BaseColumns {
        public static final String ID_MOVIE = "id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELESE_DATE = "release_date";
    }

    public static final class SeriesColumns implements BaseColumns {
        public static final String ID_SERIES = "id";
        public static final String TITLE_SERIES = "title";
        public static final String OVERVIEW_SERIES = "overview";
        public static final String POSTER_PATH_SERIES = "poster_path";
        public static final String RELESE_DATE_SERIES = "release_date";
    }
}
