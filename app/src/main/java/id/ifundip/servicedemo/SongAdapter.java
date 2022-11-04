package id.ifundip.servicedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{

    private final List<Song> songs;
    private OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.iconSong);
            title = itemView.findViewById(R.id.titleSong);
        }
    }

    public SongAdapter(List<Song> songs, OnItemClickListener listener) {
        this.songs = songs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_row_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.icon.setImageResource(song.getIcon());
        holder.title.setText(song.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }
}
