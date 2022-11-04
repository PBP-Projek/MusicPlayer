package id.ifundip.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SoundService extends Service {
    static MediaPlayer player;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int file = intent.getIntExtra("song", 0);
        player = MediaPlayer.create(this, file);
        player.setOnPreparedListener(this::onPrepared);

        return START_STICKY;
    }

    private void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    public static void pausePlayer(){
        if(player != null){
            player.pause();
        }
    }

//    add resumePlayer() method here
    public static void resumePlayer(){
        if(player != null){
            player.start();
        }
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
