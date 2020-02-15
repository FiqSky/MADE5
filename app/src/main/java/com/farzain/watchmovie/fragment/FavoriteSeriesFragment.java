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
import com.farzain.watchmovie.Series;
import com.farzain.watchmovie.activity.MovieInfoActivity;
import com.farzain.watchmovie.activity.SeriesInfoActivity;
import com.farzain.watchmovie.adapter.ListMovieAdapter;
import com.farzain.watchmovie.adapter.ListSeriesAdapter;
import com.farzain.watchmovie.db.FavoriteHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteSeriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListSeriesAdapter adapter;
    private ArrayList<Series> listSeries;
    private FavoriteHelper helper;

    public FavoriteSeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_series, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_favorite_series);

        helper = FavoriteHelper.getInstance(getContext());
        listSeries = new ArrayList<>();
        adapter = new ListSeriesAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        helper.open();
        listSeries.clear();
        listSeries.addAll(helper.getAllFavoriteSeries());
        adapter.setSeriesData(listSeries);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(new ListSeriesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Series dataSeries) {
                showSelectedSeries(dataSeries);
            }
        });
        helper.close();
    }

    private void showSelectedSeries(Series series) {
        Intent moveWithObjectActivity = new Intent(getContext(), SeriesInfoActivity.class);
        moveWithObjectActivity.putExtra(SeriesInfoActivity.EXTRA_SERIES, series);
        startActivity(moveWithObjectActivity);
    }
}
