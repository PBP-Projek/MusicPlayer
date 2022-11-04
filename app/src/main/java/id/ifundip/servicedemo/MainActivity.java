package id.ifundip.servicedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isPlaying = false;
    private int currentSong = 0;
    private List<Song> songs;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvSongs = findViewById(R.id.rvSongs);

        Button btnPrevious = findViewById(R.id.btnPrevious);
        Button btnPlayStop = findViewById(R.id.buttonPlayStop);
        Button btnNext = findViewById(R.id.btnNext);

        tvTitle = findViewById(R.id.tvTitle);

        songs = Song.getSongs();

        SongAdapter songAdapter = new SongAdapter(songs, index -> {
            currentSong = index;
            playMusic(index);
        });
        rvSongs.setAdapter(songAdapter);
        rvSongs.setLayoutManager(new LinearLayoutManager(this));

        btnPrevious.setOnClickListener(this);
        btnPlayStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

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

    public void stopMusic() {
        isPlaying = false;
        stopService(new Intent(this, SoundService.class));
    }

    private void playPauseMusic() {
        if(isPlaying){
//            stopMusic();
            SoundService.pausePlayer();
            isPlaying = false;
        }else{
//            playMusic(currentSong);
            SoundService.resumePlayer();
            isPlaying = true;
        }
    }

    private void previousMusic() {
        if(currentSong > 0){
            currentSong--;
            playMusic(currentSong);
        }else {
            Toast.makeText(this, "This is first song", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextMusic() {
        if(currentSong < songs.size() - 1){
            currentSong++;
            playMusic(currentSong);
        }else {
            Toast.makeText(this, "This is last song", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnPrevious){
            previousMusic();
        }else if(view.getId() == R.id.buttonPlayStop){
            playPauseMusic();
        }else if(view.getId() == R.id.btnNext){
            nextMusic();
        }
    }
}