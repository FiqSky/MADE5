package com.farzain.watchmovie.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.farzain.watchmovie.R;
import com.farzain.watchmovie.fragment.FavoriteMovieFragment;
import com.farzain.watchmovie.fragment.FavoriteSeriesFragment;
import com.farzain.watchmovie.fragment.MovieFragment;
import com.farzain.watchmovie.fragment.SeriesFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public PageAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.title_movie,
            R.string.title_series,
    };

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovieFragment();
                break;
            case 1:
                fragment = new FavoriteSeriesFragment();
                break;
            default:
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
