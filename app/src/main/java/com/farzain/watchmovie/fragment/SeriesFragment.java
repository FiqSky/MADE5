package com.farzain.watchmovie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farzain.watchmovie.R;
import com.farzain.watchmovie.Series;
import com.farzain.watchmovie.adapter.ListSeriesAdapter;
import com.farzain.watchmovie.viewmodel.SeriesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {
    private RecyclerView rvSeriess;
    private ArrayList<Series> list = new ArrayList<>();
    private ProgressBar progressBar;
    private ListSeriesAdapter adapter;


    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        rvSeriess = view.findViewById(R.id.rv_seriess);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSeriess.setHasFixedSize(true);

        adapter = new ListSeriesAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.rv_seriess);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        SeriesViewModel seriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);
        seriesViewModel.getSeries().observe(getViewLifecycleOwner(), getSeries);
        seriesViewModel.setSeries("EXTRA_SERIES");

        Loading(true);
    }

    private Observer<ArrayList<Series>> getSeries = new Observer<ArrayList<Series>>() {
        @Override
        public void onChanged(ArrayList<Series> series) {
            if (series != null) {
                adapter.setSeriesData(series);
            }
            Loading(false);
        }
    };

    private void Loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
