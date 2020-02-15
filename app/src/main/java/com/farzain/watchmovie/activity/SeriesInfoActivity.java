package com.farzain.watchmovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.Series;
import com.farzain.watchmovie.db.FavoriteHelper;
import com.bumptech.glide.request.RequestOptions;

public class SeriesInfoActivity extends AppCompatActivity {

    public static final String EXTRA_SERIES = "extra_series";
    private FavoriteHelper helper;
    Series series = new Series();
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_info);

        TextView movieRelease = findViewById(R.id.content_release);
        TextView movieName = findViewById(R.id.content_name);
        TextView movieSynopsis = findViewById(R.id.content_sinopsis);
        ImageView moviePoster = findViewById(R.id.moviePoster);

        ProgressBar progressBar = findViewById(R.id.progressBarSeries);
        progressBar.setVisibility(View.VISIBLE);

        series = getIntent().getParcelableExtra(EXTRA_SERIES);
        helper = FavoriteHelper.getInstance(getApplicationContext());
        helper.open();

        Series dataMovie = getIntent().getParcelableExtra(EXTRA_SERIES);
        movieName.setText(dataMovie.getName());
        movieSynopsis.setText(dataMovie.getSynopsis());
        movieRelease.setText(dataMovie.getRelease());
        Glide.with(this)
                .load(series.getPhoto())
                .apply(new RequestOptions().override(350, 550))
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
        if (helper.checkSeries(series.getId())) {
            menu.findItem(R.id.favorite_menu).setIcon(R.drawable.ic_favorite_on);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.favorite_menu) {
            if (!helper.checkSeries(this.series.getId())) {
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
        helper.open();
        long result = helper.insertSeries(this.series);
        if (result > a)
            Toast.makeText(this, getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(this, getResources().getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
    }

    private void removeFromFavorite() {
        int result = helper.deleteSeries(series.getId());
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
