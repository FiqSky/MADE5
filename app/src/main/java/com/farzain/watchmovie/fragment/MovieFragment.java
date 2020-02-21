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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.activity.MovieInfoActivity;
import com.farzain.watchmovie.adapter.ListMovieAdapter;
import com.farzain.watchmovie.viewmodel.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private ListMovieAdapter adapter;
    private ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        rvMovies = view.findViewById(R.id.rv_movies);
        showRecycleview();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        rvMovies.setHasFixedSize(true);

//        adapter = new ListMovieAdapter();
//        RecyclerView recyclerView = view.findViewById(R.id.rv_movies);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        recyclerView.setAdapter(adapter);

//        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
//        movieViewModel.getMovies().observe(getViewLifecycleOwner(), getMovie);
//        movieViewModel.setMovies("EXTRA_MOVIE");
//
//        Loading(true);
    }

//    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
//        @Override
//        public void onChanged(ArrayList<Movie> movie) {
//            if (movie != null) {
//                adapter.setData(movie);
//            }
//
//            Loading(false);
//
//        }
//    };

    private void Loading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        searchMovie(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void showRecycleview() {
        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new ListMovieAdapter();
        rvMovies.setAdapter(adapter);

        MovieViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        moviesViewModel.setMovies();
        Loading(true);

        adapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                showSelectedMovie(data);
            }

        });

        if (getActivity() != null) {
            moviesViewModel.getMovies().observe(getActivity(), new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> moviesItems) {
                    if (moviesItems != null) {
                        adapter.setData(moviesItems);
                        Loading(false);
                    }

                }

            });
        }
    }

    private void showSelectedMovie(Movie movie) {
        Intent moveWithObjectActivity = new Intent(getContext(), MovieInfoActivity.class);
        moveWithObjectActivity.putExtra(MovieInfoActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }

    private void searchMovie(Menu menu) {
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        final MovieViewModel movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search_btn)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//            searchView.setQueryHint(getResources().getString(R.string.search_movie_hint));
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
                    movieViewModel.searchMovie(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equals("")) {
                        movieViewModel.searchMovie(newText);
                    }
                    return true;
                }
            });

            MenuItem searchItem = menu.findItem(R.id.search_btn);
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
}
