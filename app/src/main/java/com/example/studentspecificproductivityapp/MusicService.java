package com.example.studentspecificproductivityapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.security.Provider;

public class MusicService extends Service {
    private static final String CHANNEL_ID = "music_playback";
    private static final int NOTIFICATION_ID = 2403;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Prevent duplicate starts
        if (isPlaying) {
            return START_STICKY;
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                10, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Build and show persistent notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Playing")
                .setContentText("Playing Relaxing Music")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentIntent(pendingIntent)// Set the intent for when the user taps the notification
                .build();
        startForeground(NOTIFICATION_ID, notification);

        mediaPlayer = MediaPlayer.create(this, R.raw.relaxing_song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        isPlaying = true;

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

        isPlaying = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // Not a bound service
    }

    private void createNotificationChannel() {
        //1. Create the Notification channel with a unique ID,
        //a user visible name, and importance level
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, // Unique ID for the notification channel
                "Music Playback Channel", //user visible name for the notification channel
                NotificationManager.IMPORTANCE_DEFAULT); // Importance level for the notification channel
        channel.setDescription("Shows persistent notification for music playback");

        //2. Get the Notification manager system service
        NotificationManager manager = getSystemService(NotificationManager.class);

        //3. Register the notification channel with the system
        manager.createNotificationChannel(channel);
    }
}
