package com.example.favoritewatchmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoritewatchmovie.R;
import com.example.favoritewatchmovie.db.FavoriteContract;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends CursorAdapter {
    private static final String TAG = "TAG";

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_movie, parent, false);
    }


    public Cursor getCursor() {
        return super.getCursor();
    }


    public void bindView(View view, final Context context, final Cursor cursor) {
        Log.d(TAG, "bindView: Apakah disini?");
        if (cursor != null) {
            Log.d(TAG, "bindView: Sudah masuk sini");
//            TextView textViewTitle;
//            TextView textViewOverview;
//            ImageView imgPoster;
//            TextView textViewName;
//            TextView ReleseDate;

            TextView title;
            TextView overview;
            TextView relese;
            ImageView poster_path;

//            textViewName = view.findViewById(R.id.txtTvTitle);
//            textViewTitle = view.findViewById(R.id.txtTitle);
//            imgPoster = view.findViewById(R.id.thumb);
//            textViewOverview = view.findViewById(R.id.txtDesc);
//            ReleseDate = view.findViewById(R.id.txtRelese);

            title = view.findViewById(R.id.tvTitle);
            overview = view.findViewById(R.id.tvOverv);
            relese = view.findViewById(R.id.tvRelese);
            poster_path = view.findViewById(R.id.ivPoster_path);

            title.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.MovieColumns.TITLE));
            overview.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.MovieColumns.OVERVIEW));
            relese.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.MovieColumns.RELESE_DATE));
            Picasso.get()
                    .load(FavoriteContract.getColumnString(cursor, FavoriteContract.MovieColumns.POSTER_PATH))
//                    .load(FavoriteContract.MovieColumns.POSTER_PATH)
                    .placeholder(R.color.colorAccent)
                    .error(R.drawable.ic_image)
                    .into(poster_path);


        }
    }
}
