package com.farzain.watchmovie.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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

import com.farzain.watchmovie.R;
import com.farzain.watchmovie.Series;
import com.farzain.watchmovie.activity.ReminderActivity;
import com.farzain.watchmovie.activity.SeriesInfoActivity;
import com.farzain.watchmovie.adapter.ListSeriesAdapter;
import com.farzain.watchmovie.db.FavoriteHelper;
import com.farzain.watchmovie.db.MappingHelper;

import java.util.ArrayList;

import static com.farzain.watchmovie.db.DatabaseContract.CONTENT_URI_SERIES;

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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_favorite_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_favorite_series);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        helper = FavoriteHelper.getInstance(getContext());
        listSeries = new ArrayList<>();
        adapter = new ListSeriesAdapter(getContext());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        } else if (item.getItemId() == R.id.reminder) {
            Intent intent = new Intent(getActivity(), ReminderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        helper.open();
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_SERIES, null, null, null, null);
        listSeries.clear();
//        listSeries.addAll(helper.getAllFavoriteSeries());
        listSeries.addAll(MappingHelper.mapCursorToArrayListSeries(cursor));
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
        Uri uri = Uri.parse(CONTENT_URI_SERIES + "/" + series.getId());
        moveWithObjectActivity.setData(uri);
        moveWithObjectActivity.putExtra(SeriesInfoActivity.EXTRA_SERIES, series);
        startActivity(moveWithObjectActivity);
    }
}
