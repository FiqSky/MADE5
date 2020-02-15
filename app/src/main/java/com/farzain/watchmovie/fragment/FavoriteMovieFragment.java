package com.farzain.watchmovie.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.activity.MovieInfoActivity;
import com.farzain.watchmovie.adapter.ListMovieAdapter;
import com.farzain.watchmovie.db.FavoriteHelper;

import java.util.ArrayList;

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

        helper = FavoriteHelper.getInstance(getContext());
        listMovie = new ArrayList<>();
        adapter = new ListMovieAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        helper.open();
        listMovie.clear();
        listMovie.addAll(helper.getAllFavoriteMovie());
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
        moveWithObjectActivity.putExtra(MovieInfoActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }
}
