package id.ifundip.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MusikActivity extends AppCompatActivity implements View.OnClickListener {
    //    deklarasi variabel
    TextView position;
    static TextView duration;
    TextView tvTitle;
    static SeekBar seekBar;
    ImageView btPrev, btPlay, btPause, btNext;
    private boolean isPlaying = true;
    private int currentSong = 0;
    private List<Song> songs;

    static Handler handler = new Handler();
    static Runnable runnable;

    //    konstanta untuk key pada intent
    public final static String EXTRA_INDEX = "extra_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musik);

        //Assign variable
        position = findViewById(R.id.position);
        duration = findViewById(R.id.duration);
        seekBar = findViewById(R.id.seek_bar);
        btPrev = findViewById(R.id.bt_prev);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btNext = findViewById(R.id.bt_next);
        tvTitle = findViewById(R.id.tv_title);

        // menambahkan lagu
        songs = Song.getSongs();
        //        menerima index dari intent
        currentSong = getIntent().getIntExtra(EXTRA_INDEX, 0);

//        memainkan musik
        playMusic(currentSong);

//        asynctask untuk memperbarui/menjalankan seekbar
        runnable = new Runnable() {
            @Override
            public void run() {
                // Set progress on seekbar
                position.setText(convertFormat(SoundService.getCurrentPosition()));
                seekBar.setProgress(SoundService.getCurrentPosition() / 1000);
                // Handler post delay for 1 second
                handler.postDelayed(this, 1000);
            }
        };

        // Set on click listener
        btPlay.setOnClickListener(this);
        btPause.setOnClickListener(this);
        btPrev.setOnClickListener(this);
        btNext.setOnClickListener(this);
    }

    //    digunakan untuk mengatur button ketika diklik
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_play:
                // hiding play btn
                btPlay.setVisibility(View.GONE);
                // showing pause btn
                btPause.setVisibility(View.VISIBLE);
                // Start music
                SoundService.resumePlayer();
                break;
            case R.id.bt_pause:
                // hiding pause btn
                btPause.setVisibility(View.GONE);
                // showing play btn
                btPlay.setVisibility(View.VISIBLE);
                // Pause music
                SoundService.pausePlayer();
                break;
            case R.id.bt_prev:
                // Play previous music
                previousMusic();
                break;
            case R.id.bt_next:
                // Play next music
                nextMusic();
                break;
        }
    }

    //    menjalankan servis
    public void playMusic(int index) {
        if (isPlaying) {
            stopMusic();
        }
        isPlaying = true;
        tvTitle.setText(songs.get(index).getTitle());
        Intent intent = new Intent(this, SoundService.class);
        intent.putExtra("song", songs.get(index).getFile());
        startService(intent);
    }

    //    set durasi seekbar
    public static void setDuration() {
        // Get duration of music
        int musicDuration = SoundService.getDuration();
        // int  to millisecond
        String sDuration = convertFormat(musicDuration);
        // Set duration on text view
        duration.setText(sDuration);
        seekBar.setMax(musicDuration / 1000);
        // Initialize runnable
        handler.postDelayed(runnable, 0);
    }

    //    menghentikan servis
    public void stopMusic() {
        isPlaying = false;
        stopService(new Intent(this, SoundService.class));
    }

    //    memainkan lagu sebelumnya
    private void previousMusic() {
        if (currentSong > 0) {
            currentSong--;
            playMusic(currentSong);
        } else {
            Toast.makeText(this, "This is first song", Toast.LENGTH_SHORT).show();
        }
    }

    //    memainkan lagu selanjutnya
    private void nextMusic() {
        if (currentSong < songs.size() - 1) {
            currentSong++;
            playMusic(currentSong);
        } else {
            Toast.makeText(this, "This is last song", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("DefaultLocale")
    private static String convertFormat(int musicDuration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(musicDuration), TimeUnit.MILLISECONDS.toSeconds(musicDuration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(musicDuration)));
    }
}