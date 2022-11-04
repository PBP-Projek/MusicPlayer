package id.ifundip.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SoundService extends Service {
    static MediaPlayer player;

//    onStartCommand dijalankan ketika service dijalankan
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        mengambil data dari intent
        int file = intent.getIntExtra("song", 0);

//        membuat objek MediaPlayer
        player = MediaPlayer.create(this, file);

//        onPrepared dijalankan ketika media player siap
        player.setOnPreparedListener(this::onPrepared);

        return START_STICKY;
    }

//    onPrepared dijalankan ketika media player siap
    private void onPrepared(MediaPlayer mediaPlayer) {
//        memutar lagu
        mediaPlayer.start();
    }

//    onDestroy dijalankan ketika service dihentikan
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

//    stopPlayer untuk menghentikan media player
    public static void pausePlayer(){
        if(player != null){
            player.pause();
        }
    }

//    stopPlayer untuk menghentikan media player
    public static void resumePlayer(){
        if(player != null){
            player.start();
        }
    }

//    stopPlayer untuk menghentikan media player
    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

//    onBind digunakan untuk menghubungkan service dengan activity
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
