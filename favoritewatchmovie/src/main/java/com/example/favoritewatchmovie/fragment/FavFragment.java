package com.example.favoritewatchmovie.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.favoritewatchmovie.R;
import com.example.favoritewatchmovie.adapter.FavAdapter;
import com.example.favoritewatchmovie.model.Fav;

import java.util.ArrayList;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FavFragment extends Fragment {
    private View view;
    private FavAdapter adapter;
    private static final String PROVIDER_NAME = "com.farzain.watchmovie.db.FavoriteProvider";
    private static final String URL = "content://" + PROVIDER_NAME;
    private static final Uri CONTENT_URI = Uri.parse(URL);

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fav, container, false);
       Log.d(TAG, "onCreateView: Sudah disini");
        initView();
        return view;
    }

    private void initView() {
        Log.d(TAG, "initView: Sudah disini ");
        RecyclerView dataMovie = view.findViewById(R.id.rv_movies);

        adapter = new FavAdapter(getContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        dataMovie.setLayoutManager(gridLayoutManager);
        dataMovie.setHasFixedSize(true);
        dataMovie.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        Cursor cursor = Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI,
                null,
                null,
                null,
                null);
        Log.d(TAG, "loadData: Sampai disini");

        if (cursor != null) {
            Log.d(TAG, "loadData: Yeyy sudah disini");
            adapter.clear();
            ArrayList<Fav> favoriteList = new ArrayList<>();
            Log.d(TAG, "loadData: lanjut sudah disini");
            String title, overview, relese, poster_path;
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    title = cursor.getString(cursor.getColumnIndex("title"));
                    Log.d(TAG, "loadData: "+ title);
                    overview = cursor.getString(cursor.getColumnIndexOrThrow("overview"));
                    relese = cursor.getString(cursor.getColumnIndexOrThrow("release_date"));
                    poster_path = cursor.getString(cursor.getColumnIndexOrThrow("poster_path"));
                    Fav favorite = new Fav(title,overview,relese,poster_path);
                    favoriteList.add(favorite);
                    cursor.moveToNext();
                }
            }
        }
    }
}
