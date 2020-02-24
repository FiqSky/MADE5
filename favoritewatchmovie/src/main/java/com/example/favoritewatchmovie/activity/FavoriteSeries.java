package com.example.favoritewatchmovie.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.favoritewatchmovie.MainActivity;
import com.example.favoritewatchmovie.R;
import com.example.favoritewatchmovie.adapter.SeriesAdapter;
import com.example.favoritewatchmovie.db.FavoriteContract;

import org.jetbrains.annotations.NotNull;

public class FavoriteSeries extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int LOAD_FAVORITE_SERIES_ID = 220;
    private SeriesAdapter seriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_series);
        setActionBarTitle("Favorite Series");

        ListView listView = findViewById(R.id.list_view);
        seriesAdapter = new SeriesAdapter(this, null, true);
        listView.setAdapter(seriesAdapter);
        getSupportLoaderManager().initLoader(LOAD_FAVORITE_SERIES_ID, null, this);
    }

    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAVORITE_SERIES_ID, null, this);
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @NotNull
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, FavoriteContract.CONTENT_URI_SERIES,
                null,
                null,
                null,
                null);
    }

    public void onLoadFinished(@NotNull Loader<Cursor> loader, Cursor data) {
        seriesAdapter.swapCursor(data);
    }


    public void onLoaderReset(@NotNull Loader<Cursor> loader) {
        seriesAdapter.swapCursor(null);
    }

    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAVORITE_SERIES_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menu = item.getItemId();
        if (menu == R.id.movie) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
