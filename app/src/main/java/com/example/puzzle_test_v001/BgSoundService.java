package com.example.puzzle_test_v001;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BgSoundService extends Service {

    private MediaPlayer mp;
    private String operation = "";


    public BgSoundService() { super(); }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.f1);
        mp.setLooping(true);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        try {
            if (intent != null) {
                operation = intent.getStringExtra("operation");
                switch (operation) {
                    case "start":
                        mp.start();
                        SingletonServiceManager.isBackgroundSoundService = true;
                        break;
                    case "pause":
                        mp.pause();
                        SingletonServiceManager.isBackgroundSoundService = false;
                        break;
                    default:
                        break;
                }
            }
        }catch (NullPointerException e){
            e.getStackTrace();
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        mp.stop();
        mp.release();
    }


    public static class SingletonServiceManager {
        public static boolean isBackgroundSoundService = true;
        public static boolean isBackgroundSoundServiceRunning() {
            return isBackgroundSoundService;
        }

    }
}
