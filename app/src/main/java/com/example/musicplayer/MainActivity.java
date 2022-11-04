package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int READ_EXTERNAL_STORAGE = 1;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        ArrayList<AudioData> list = loadAudio(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicListAdapter(list));

        button = findViewById(R.id.button);
    }

    //checkPermission
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
            } else {
                //permission denied
            }
        }
    }

    private ArrayList<AudioData> loadAudio(Context context) {
        ArrayList<AudioData> tmpList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor!= null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String path = cursor.getString(1);
                tmpList.add(new AudioData(name, path));
            }
            cursor.close();
        }
        return tmpList;
    }

    private class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder> {

        private ArrayList<AudioData> list;

        public MusicListAdapter(ArrayList<AudioData> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_layout,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(list.get(position).getName());
            holder.textView.setOnClickListener(e->{
                MediaPlayer player = MediaPlayer.create(e.getContext(), Uri.parse(list.get(position).getPath()));
                player.start();
            });
            button.setOnClickListener(e->{
                MediaPlayer player = MediaPlayer.create(e.getContext(), Uri.parse(list.get(position).getPath()));
                player.start();
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        protected class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}