package com.example.android.securestan;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GeofenceRegistrationService extends IntentService  {
    String url1="",status="";

    private static final String TAG = "GeoIntentService";

    public GeofenceRegistrationService() {
        super(TAG);
    }
    private void generateNotification(String locationId, String address) {
        long when = System.currentTimeMillis();
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.putExtra("id", locationId);
        notifyIntent.putExtra("address", address);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.inti)
                        .setContentTitle(locationId)
                        .setContentText(address)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setWhen(when);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) when, builder.build());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "GeofencingEvent error " + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            for (Geofence geofence : geofences) {
                generateNotification(geofence.getRequestId(), "Location activated");
            }

            Geofence geofence = geofences.get(0);
            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_STAN_UNI)) {
                Log.d(TAG, "You are inside UMIT");


                status = "IN";
                url1 = "https://api.ample90.hasura-app.io/status";

                try {
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    JSONObject jsonObject = new JSONObject()
                            .put("status", status);

                    Log.d(TAG, "we are inside try block ");

                    RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
                    Request request = new Request.Builder()
                            .url(url1)
                            .post(body)
                            .build();
                    Log.d(TAG, "json object is" + jsonObject.toString());
                    Log.d(TAG, "request is " + request);
                    client.newCall(request).enqueue(new Callback() {
                        @Override

                        public void onFailure(okhttp3.Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                            if (!response.isSuccessful()) {
                                Log.d(TAG, "Response not successful");

                                throw new IOException("Unexpected code" + response);
                            }
                        }
                    });

                    // To execute the call synchronously
                    // try {
                    //     Response response = client.newCall(request).execute();
                    //     String responseString = response.body().string(); // handle response
                    // } catch (IOException e) {
                    //     e.printStackTrace(); // handle error
                    // }

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            } else {
                if(transaction == Geofence.GEOFENCE_TRANSITION_EXIT) {
                    Log.d(TAG, "You are outside UMIT");
                    status = "OUT";
                    url1 = "https://api.ample90.hasura-app.io/status";

                    try {
                        OkHttpClient client = new OkHttpClient();
                        MediaType mediaType = MediaType.parse("application/json");
                        JSONObject jsonObject = new JSONObject()
                                .put("status", status);

                        Log.d(TAG, "we are inside try block ");

                        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
                        Request request = new Request.Builder()
                                .url(url1)
                                .post(body)
                                .build();
                        Log.d(TAG, "json object is" + jsonObject.toString());
                        Log.d(TAG, "request is " + request);
                        client.newCall(request).enqueue(new Callback() {
                            @Override

                            public void onFailure(okhttp3.Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    Log.d(TAG, "Response not successful");

                                    throw new IOException("Unexpected code" + response);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }

}


