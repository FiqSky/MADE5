package com.farzain.watchmovie.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farzain.watchmovie.Series;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.activity.SeriesInfoActivity;
import com.farzain.watchmovie.activity.ReminderActivity;
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
        setHasOptionsMenu(true);
        showRecycleview();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        searchSeries(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if (item.getItemId() == R.id.reminder) {
            Intent intent = new Intent(getActivity(), ReminderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecycleview() {
        rvSeriess.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new ListSeriesAdapter(list);
        adapter.notifyDataSetChanged();
        rvSeriess.setAdapter(adapter);

        SeriesViewModel seriesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SeriesViewModel.class);
        seriesViewModel.setSeries();
        Loading(true);

        adapter.setOnItemClickCallback(new ListSeriesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Series data) {
                showSelectedSeries(data);
            }
        });

        if (getActivity() != null) {
            seriesViewModel.getSeries().observe(getActivity(), new Observer<ArrayList<Series>>() {
                @Override
                public void onChanged(ArrayList<Series> seriesItems) {
                    if (seriesItems != null) {
                        adapter.setSeriesData(seriesItems);
                        Loading(false);
                    }

                }

            });
        }
    }

    private void showSelectedSeries(Series series) {
        Intent moveWithObjectActivity = new Intent(getContext(), SeriesInfoActivity.class);
        moveWithObjectActivity.putExtra(SeriesInfoActivity.EXTRA_SERIES, series);
        startActivity(moveWithObjectActivity);
    }

    private void searchSeries(Menu menu) {
        final SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        final SeriesViewModel seriesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SeriesViewModel.class);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_series_hint));
            searchView.setIconifiedByDefault(false);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.setQuery(query, false);
                    searchView.setIconified(false);
                    searchView.clearFocus();
                    seriesViewModel.searchSeries(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equals("")) {
                        seriesViewModel.searchSeries(newText);
                    }
                    return true;
                }
            });

            MenuItem searchItem = menu.findItem(R.id.search);
            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    showRecycleview();
                    return true;
                }
            });
        }
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

