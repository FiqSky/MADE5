package com.farzain.watchmovie.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.activity.MovieInfoActivity;
import com.farzain.watchmovie.activity.ReminderActivity;
import com.farzain.watchmovie.adapter.ListMovieAdapter;
import com.farzain.watchmovie.db.FavoriteHelper;
import com.farzain.watchmovie.db.MappingHelper;

import java.util.ArrayList;

import static com.farzain.watchmovie.db.DatabaseContract.CONTENT_URI_MOVIE;

public class FavoriteMovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListMovieAdapter adapter;
    private ArrayList<Movie> listMovie;
    private FavoriteHelper helper;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_favorite_movie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        helper = FavoriteHelper.getInstance(getContext());
        listMovie = new ArrayList<>();
        adapter = new ListMovieAdapter();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.search_btn).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent intent = new Intent(getActivity(), ReminderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        helper.open();
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        listMovie.clear();
//        listMovie.addAll(helper.getAllFavoriteMovie());
        listMovie.addAll(MappingHelper.mapCursorToArrayListMovie(cursor));
        adapter.setData(listMovie);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                showSelectedMovie(data);
            }

        });
        helper.close();
    }

    private void showSelectedMovie(Movie movie) {
        Intent moveWithObjectActivity = new Intent(getContext(), MovieInfoActivity.class);
        Uri uri = Uri.parse(CONTENT_URI_MOVIE + "/" + movie.getId());
        moveWithObjectActivity.setData(uri);
        moveWithObjectActivity.putExtra(MovieInfoActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }
}
