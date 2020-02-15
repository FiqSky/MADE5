package com.farzain.watchmovie.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farzain.watchmovie.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = "90f828ee41b521c823dd351d6b67affa";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setMovies(final String movie) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        Log.d(TAG, "setMovies: " + url);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movieObject = list.getJSONObject(i);
                        Movie movieItems = new Movie();
                        movieItems.setId(movieObject.getInt("id"));
                        movieItems.setName(movieObject.getString("title"));
                        movieItems.setSynopsis(movieObject.getString("overview"));
                        movieItems.setRelease(movieObject.getString("release_date"));
                        movieItems.setPhoto("https://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path"));
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}
