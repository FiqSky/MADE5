package com.farzain.watchmovie.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farzain.watchmovie.Series;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SeriesViewModel extends ViewModel {
    private static final String API_KEY = "90f828ee41b521c823dd351d6b67affa";
    private MutableLiveData<ArrayList<Series>> listSeries = new MutableLiveData<>();

    public void setSeries(final String series) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Series> listItem = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        Log.d(TAG, "setSeries: " + url);

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {

                        JSONObject seriesObject = list.getJSONObject(i);
                        Series seriesItems = new Series();
                        seriesItems.setId(seriesObject.getInt("id"));
                        seriesItems.setName(seriesObject.getString("name"));
                        seriesItems.setSynopsis(seriesObject.getString("overview"));
                        seriesItems.setRelease(seriesObject.getString("first_air_date"));
                        seriesItems.setPhoto("https://image.tmdb.org/t/p/w185" + seriesObject.getString("poster_path"));
                        listItem.add(seriesItems);
                    }
                    listSeries.postValue(listItem);
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

    public LiveData<ArrayList<Series>> getSeries() {
        return listSeries;
    }
}
