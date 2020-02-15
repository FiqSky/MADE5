package com.farzain.watchmovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.db.FavoriteHelper;

public class MovieInfoActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private FavoriteHelper helper;
    Movie movie = new Movie();
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        TextView movieRelease = findViewById(R.id.content_release);
        TextView movieName = findViewById(R.id.content_name);
        TextView movieSynopsis = findViewById(R.id.content_sinopsis);
        ImageView moviePoster = findViewById(R.id.moviePoster);

        ProgressBar progressBar = findViewById(R.id.progressBarInfo);
        progressBar.setVisibility(View.VISIBLE);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        helper = FavoriteHelper.getInstance(getApplicationContext());
        helper.open();

        movieName.setText(movie.getName());
        Log.d(EXTRA_MOVIE, "onCreate: " + movie.getName());
        movieSynopsis.setText(movie.getSynopsis());
        movieRelease.setText(movie.getRelease());
        Glide.with(MovieInfoActivity.this)
                .load(movie.getPhoto())
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(moviePoster);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (helper.checkMovie(movie.getId())) {
            menu.findItem(R.id.favorite_menu).setIcon(R.drawable.ic_favorite_on);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.favorite_menu) {
            if (!helper.checkMovie(this.movie.getId())) {
                item.setIcon(R.drawable.ic_favorite_on);
                helper.open();
                addToFavorite();
            } else {
                item.setIcon(R.drawable.ic_favorite_off);
                removeFromFavorite();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToFavorite() {
        long result = helper.insertMovie(this.movie);
        if (result > a)
            Toast.makeText(this, getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(this, getResources().getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
    }

    private void removeFromFavorite() {
        int result = helper.deleteMovie(movie.getId());
        if (result > a) {
            Toast.makeText(this, getResources().getString(R.string.removed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.failed_remove), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}