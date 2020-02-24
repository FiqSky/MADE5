package com.example.favoritewatchmovie;

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

import com.example.favoritewatchmovie.activity.FavoriteSeries;
import com.example.favoritewatchmovie.adapter.MovieAdapter;
import com.example.favoritewatchmovie.db.FavoriteContract;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int LOAD_FAVORITE_ID = 110;
    private MovieAdapter movieAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBarTitle("Favorite Movie");

        ListView listView = findViewById(R.id.list_view);
        movieAdapter = new MovieAdapter(this, null, true);
        listView.setAdapter(movieAdapter);
        getSupportLoaderManager().initLoader(LOAD_FAVORITE_ID, null, this);

    }

    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAVORITE_ID, null, this);
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @NotNull
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, FavoriteContract.CONTENT_URI_MOVIE,
                null,
                null,
                null,
                null);
    }

    public void onLoadFinished(@NotNull Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }


    public void onLoaderReset(@NotNull Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAVORITE_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menu = item.getItemId();
//        if (menu == R.id.movie){
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
        if (menu == R.id.series) {
            Intent intent = new Intent(this, FavoriteSeries.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
