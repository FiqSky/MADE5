package com.farzain.watchmovie.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.farzain.watchmovie.Movie;
import com.farzain.watchmovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ReleaseReminderReceiver extends BroadcastReceiver {
    private static final String API_KEY = "90f828ee41b521c823dd351d6b67affa";
    private static final String EXTRA_TYPE = "type";
    private static int RELEASE_ID = 102;
    private ArrayList<Movie> listItemMovie = new ArrayList<>();

    public ReleaseReminderReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final int reminder = intent.getIntExtra(EXTRA_TYPE, RELEASE_ID);
        if (reminder == RELEASE_ID) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
            final String today = day.format(new Date());
            AsyncHttpClient client = new AsyncHttpClient();

            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
            Log.d(TAG, "setMovies: " + url);

            client.get(url, new JsonHttpResponseHandler() {
                @SuppressLint("ResourceType")
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                    super.onSuccess(statusCode, headers, responseBody);
                    try {
                        JSONArray list = responseBody.getJSONArray("results");

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject movieObject = list.getJSONObject(i);
                            Movie movieItems = new Movie();
                            movieItems.setId(movieObject.getInt("id"));
                            movieItems.setName(movieObject.getString("title"));
                            movieItems.setSynopsis(movieObject.getString("overview"));
                            movieItems.setRelease(movieObject.getString("release_date"));
                            movieItems.setPhoto("https://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path"));
                            listItemMovie.add(movieItems);
                            showAlarmNotification(context, context.getResources().getString(R.string.reminder_release), null, reminder, movieItems);
                        }
                    } catch (JSONException e) {
                        Log.d("Exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }
    }

    public void releaseReminderOn(Context context) {
        releaseReminderOff(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(context));
    }

    public void releaseReminderOff(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }

    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);
        return PendingIntent.getBroadcast(context, RELEASE_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void showAlarmNotification(Context context, String title, @Nullable String message, int notifId, @Nullable Movie item) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager chanel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        if (listItemMovie.size() != 0) {
            for (int i = 0; i < listItemMovie.size(); i++) {
                inboxStyle.addLine(listItemMovie.get(i).getName());
            }
        }
        NotificationCompat.Builder builder;
        if (message == null) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(title)
                    .setStyle(inboxStyle)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent));
        } else {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent));
        }

        PendingIntent pendingIntent = getPendingIntent(context);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(RELEASE_ID, notification);
        }
    }
}
