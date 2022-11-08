package id.ifundip.servicedemo;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private final int icon;
    private final String title;
    private final int file;

    public Song(int icon, String title, int file) {
        this.icon = icon;
        this.title = title;
        this.file = file;
    }

    public int getIcon() {
        return icon;
    }


    public String getTitle() {
        return title;
    }

    public int getFile() {
        return file;
    }


    public static List<Song> getSongs(){
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(R.drawable.ic_song_file,"Fanfare",R.raw.fanfare));
        songs.add(new Song(R.drawable.ic_song_file,"Harmoni",R.raw.harmoni));
        songs.add(new Song(R.drawable.ic_song_file,"Piano 1 Stanza",R.raw.piano_1_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Piano 3 Stanza",R.raw.piano_3_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Piano & Paduan Suara 1 Stanza",R.raw.piano_paduan_suara_1_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Piano & Paduan Suara 3 Stanza",R.raw.piano_paduan_suara_3_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Simphoni 1 Stanza",R.raw.simphoni_1_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Simphoni 3 Stanza",R.raw.simphoni_3_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Simphoni & Vokal 1 Stanza",R.raw.simphoni_vokal_1_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Simphoni & Vokal 3 Stanza",R.raw.simphoni_vokal_3_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Unisono 1 Stanza",R.raw.unisono_1_stanza));
        songs.add(new Song(R.drawable.ic_song_file,"Unisono 1 Stanza",R.raw.unisono_3_stanza));
        return songs;
    }
}