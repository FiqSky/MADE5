package com.farzain.watchmovie.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.net.Uri;

import java.util.List;

import static com.farzain.watchmovie.db.DatabaseContract.AUTHORITY;
import static com.farzain.watchmovie.db.DatabaseContract.CONTENT_URI_MOVIE;
import static com.farzain.watchmovie.db.DatabaseContract.CONTENT_URI_SERIES;
import static com.farzain.watchmovie.db.DatabaseContract.TABLE_MOVIE;
import static com.farzain.watchmovie.db.DatabaseContract.TABLE_SERIES;

public class FavoriteProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.miftahjuanda.movies";
    static final String URL = "content://" + PROVIDER_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);
    private Realm realm;
    private RealmHelper realmHelper;

    public FavoriteProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);
        return getCursorFromList(realmHelper.getFavoritListTv(),realmHelper.getFavoritList());
    }

    public Cursor getCursorFromList(List<FavoriteTv> FavTv, List<Favorite> Movie) {
        MatrixCursor cursor = new MatrixCursor(new String[] {"image", "title","releasedate",
                "description","vote"}
        );

        for ( FavoriteTv favoriteTv : FavTv ) {
            cursor.newRow()
                    .add("image", favoriteTv.getPosterPath())
                    .add("title", favoriteTv.getTitle())
                    .add("releasedate", favoriteTv.getReleaseDate())
                    .add("description", favoriteTv.getOverview())
                    .add("vote", favoriteTv.getVoteAverage());
        }

        for ( Favorite favorite : Movie ) {
            cursor.newRow()
                    .add("image", favorite.getPosterPath())
                    .add("title", favorite.getTitle())
                    .add("releasedate", favorite.getReleaseDate())
                    .add("description", favorite.getOverview())
                    .add("vote", favorite.getVoteAverage());
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /*
    Integer digunakan sebagai identifier antara select all sama select by id
     *//*
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int SERIES = 3;
    private static final int SERIES_ID = 4;
    private FavoriteHelper helper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    *//*
    Uri matcher untuk mempermudah identifier dengan menggunakan integer
     *//*
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

    public FavoriteProvider() {
    }

    @Override
    public boolean onCreate() {
        helper = FavoriteHelper.getInstance(getContext());
        helper.open();
        return true;
    }

    *//*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     *//*
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
        int delete;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                delete = helper.deleteMovieProvider(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                break;
            case SERIES_ID:
                delete = helper.deleteSeriesProvider(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_SERIES, null);
                break;
            default:
                delete = 0;
                break;
        }
        return delete;
    }*/
}
