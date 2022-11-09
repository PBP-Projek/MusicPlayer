package id.ifundip.servicedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    deklarasi variabel
//    songs untuk menyimpan data lagu dalam bentuk Song(tipe data bentukan)
    private List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        inisialisasi variabel dengan id
        RecyclerView rvSongs = findViewById(R.id.rvSongs);

//        menambahkan lagu
        songs = Song.getSongs();

//        menambahkan listener
        SongAdapter songAdapter = new SongAdapter(songs, index -> {
//            ketika lagu dipilih maka akan memanggil menjalankan perintah di bawah dan mengirimkan index dari lagu tsb
            Intent intent = new Intent(MainActivity.this, MusikActivity.class);
            intent.putExtra(MusikActivity.EXTRA_INDEX, index);
            startActivity(intent);
            });

//        menambahkan adapter
        rvSongs.setAdapter(songAdapter);
        rvSongs.setLayoutManager(new LinearLayoutManager(this));
    }
}