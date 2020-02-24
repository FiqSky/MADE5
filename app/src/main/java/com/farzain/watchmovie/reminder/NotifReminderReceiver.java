//package com.farzain.watchmovie.reminder;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//
//import com.farzain.watchmovie.MainActivity;
//import com.farzain.watchmovie.Movie;
//import com.farzain.watchmovie.R;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.SyncHttpClient;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.lang.ref.WeakReference;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Objects;
//
//import cz.msebera.android.httpclient.Header;
//
//import static com.farzain.watchmovie.activity.ReminderActivity.ACTION1;
//import static com.farzain.watchmovie.activity.ReminderActivity.ACTION2;
//import static com.farzain.watchmovie.activity.ReminderActivity.REQUEST_CODE;
//
//public class NotifReminderReceiver extends BroadcastReceiver {
//
//    public static final int NOTIFICATION_ID = 1;
//    private final static String GROUP_KEY_EMAILS = "group_keys_movies";
//    public static String CHANNEL_ID = "channel_01";
//    public static CharSequence CHANNEL_NAME = "movies_channel";
//    private Intent reminderIntent;
//    private Context reminderContext;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        reminderIntent = intent;
//        reminderContext = context;
//        Log.d("Hello", "Tell me more");
////        new ShowAsync(this).execute();
//        if (Objects.equals(reminderIntent.getAction(), ACTION1)) {
//            Log.d("Hello", "Tell me");
//            sendNotif1(context);
//        }
//    }
//
//    private void sendNotif1(Context context) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent repeatingIntent = new Intent(context, MainActivity.class);
//        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setContentTitle(context.getResources().getString(R.string.reminder_release))
//                .setContentText(context.getResources().getString(R.string.reminder_daily_message))
//                .setAutoCancel(true);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//            mBuilder.setChannelId(CHANNEL_ID);
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//
//        Notification notification = mBuilder.build();
//
//        if (notificationManager != null) {
//            notificationManager.notify(NOTIFICATION_ID, notification);
//        }
//    }
//
//    private void sendNotif2(Context context, int position, ArrayList<Movie> shows) {
//        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent repeatingIntent = new Intent(context, MainActivity.class);
//        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder mBuilder;
//
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
//                .addLine(shows.get(shows.size() - 1).getName() + " " + context.getString(R.string.has_been_released))
//                .addLine(shows.get(shows.size() - 2).getName() + " " + context.getString(R.string.has_been_released))
//                .addLine(shows.get(shows.size() - 3).getName() + " " + context.getString(R.string.has_been_released))
//                .setBigContentTitle(context.getString(R.string.three_of) + " " + (position + 1) + " " + context.getString(R.string.new_shows));
//        mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setContentIntent(pendingIntent)
//                .setContentTitle(position + 1 + " " + context.getString(R.string.new_shows))
//                .setContentText(context.getString(R.string.check_these))
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setGroup(GROUP_KEY_EMAILS)
//                .setGroupSummary(true)
//                .setStyle(inboxStyle)
//                .setAutoCancel(true);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//            mBuilder.setChannelId(CHANNEL_ID);
//            if (mNotificationManager != null) {
//                mNotificationManager.createNotificationChannel(channel);
//            }
//        }
//        Notification notification = mBuilder.build();
//        if (mNotificationManager != null) {
//            mNotificationManager.notify(position, notification);
//        }
//    }
//
//    @Override
//    public void preExecute() {
//    }
//
//    @Override
//    public void postExecute(ArrayList<Movie> shows) {
//        if (Objects.equals(reminderIntent.getAction(), ACTION2)) {
//            for (int i = 0; i < shows.size(); i++) {
//                if (i == shows.size() - 1) {
//                    sendNotif2(reminderContext, i, shows);
//                }
//            }
//        }
//    }
//    private static class ShowAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
//
//        /*private final WeakReference<Callbac> weakCallback;
//
//        private ShowAsync(LoadShowsCallback callback) {
//            weakCallback = new WeakReference<>(callback);
//        }*/
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            weakCallback.get().preExecute();
//        }
//
//        @Override
//        protected ArrayList<Movie> doInBackground(Void... voids) {
//            String API_KEY = "90f828ee41b521c823dd351d6b67affa";
//            final ArrayList<Movie> shows = new ArrayList<>();
//
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//            Date date = calendar.getTime();
//            String strDate = formatter.format(date);
//            SyncHttpClient client = new SyncHttpClient();
//
//            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + strDate + "&primary_release_date.lte=" + strDate;
//            client.get(url, new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    try {
//                        String response = new String(responseBody);
//                        JSONObject responseObject = new JSONObject(response);
//                        JSONArray listResults = responseObject.getJSONArray("results");
//
//                        for (int i = 0; i < listResults.length(); i++) {
//                            JSONObject object = listResults.getJSONObject(i);
//                            Movie show = new Movie();
//                            shows.add(show);
//                            Log.d("Hello", shows.get(i).getName());
//                        }
//                    } catch (Exception e) {
//                        Log.d("Exception", e.getMessage());
//                    }
//
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                }
//            });
//            return shows;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Movie> shows) {
//            super.onPostExecute(shows);
////            weakCallback.get().postExecute(shows);
//        }
//    }
//}
