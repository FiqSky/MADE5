package com.farzain.watchmovie.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.R;
import com.farzain.watchmovie.Series;
import com.farzain.watchmovie.db.FavoriteHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteStackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> widgetItems = new ArrayList<>();
    private final Context context;
    private FavoriteHelper helper;

    public FavoriteStackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        helper = FavoriteHelper.getInstance(context);
    }

    @Override
    public void onDataSetChanged() {
        helper.open();
        ArrayList<Movie> listMovie = helper.getAllFavoriteMovie();
        for (int i = 0; i < listMovie.size(); i++) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(listMovie.get(i).getPhoto())
                        .submit(512, 512)
                        .get();
            } catch (Exception e) {
                e.getMessage();
            }
            widgetItems.add(bitmap);
        }
        ArrayList<Series> listSeries = helper.getAllFavoriteSeries();
        for (int i = 0; i < listSeries.size(); i++) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(listSeries.get(i).getPhoto())
                        .submit(512, 512)
                        .get();
            } catch (Exception e) {
                e.getMessage();
            }
            widgetItems.add(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        helper.close();
    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, widgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(FavoriteImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
