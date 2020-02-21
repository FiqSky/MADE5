package com.farzain.watchmovie.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import static com.farzain.watchmovie.db.DatabaseContract.AUTHORITY;
import static com.farzain.watchmovie.db.DatabaseContract.CONTENT_URI_MOVIE;
import static com.farzain.watchmovie.db.DatabaseContract.CONTENT_URI_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.TABLE_MOVIE;
import static com.farzain.watchmovie.db.DatabaseContract.TABLE_SERIES;

public class FavoriteProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int SERIES = 3;
    private static final int SERIES_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_MOVIE + "/#",
                MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_SERIES, SERIES);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_SERIES + "/#",
                SERIES_ID);
    }

    private FavoriteHelper helper;

    public FavoriteProvider() {
    }

    @Override
    public boolean onCreate() {
        helper.open();
        helper = FavoriteHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = helper.queryMovie();
                break;
            case MOVIE_ID:
                cursor = helper.queryMovieById(uri.getLastPathSegment());
                break;
            case SERIES:
                cursor = helper.querySeries();
                break;
            case SERIES_ID:
                cursor = helper.querySeriesById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri mUri;
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = helper.insertMovieProvider(values);
                mUri = Uri.parse(CONTENT_URI_MOVIE + "/" + added);
                getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                break;
            case SERIES:
                added = helper.insertSeriesProvider(values);
                mUri = Uri.parse(CONTENT_URI_SERIES + "/" + added);
                getContext().getContentResolver().notifyChange(CONTENT_URI_SERIES, null);
                break;
            default:
                throw new SQLException("FailedAdded " + uri);
        }

        return mUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int drop;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                drop = helper.deleteMovieProvider(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                break;
            case SERIES_ID:
                drop = helper.deleteSeriesProvider(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_SERIES, null);
                break;
            default:
                drop = 0;
                break;
        }
        return drop;
    }
}
