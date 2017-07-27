package digitalhouse.android.View.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Model.POJO.Album;
import digitalhouse.android.Model.POJO.Track;
import digitalhouse.android.Util.GlideApp;
import digitalhouse.android.a0317moacns1c_01.R;

public class AdapterFavorites extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Track> favoriteTracks = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;
    private String songArtist;
    private String songAlbumImage;

    //constructor
    public AdapterFavorites(List<Track> favoriteTracks, Context context) {
        this.favoriteTracks = favoriteTracks;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        onClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //neuvo inflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.favourites_cell, parent, false);
        //nuev viewHolder
        ViewHolderFavorites viewHolderFavorites = new ViewHolderFavorites(view);

        //asignar la vista al onCLickListener
        view.setOnClickListener(this);

        return viewHolderFavorites;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //Extraer una cancion en una determinada posicion
        Track track = favoriteTracks.get(position);

        //nuevo viewHolder
        ViewHolderFavorites viewHolderFavorites = (ViewHolderFavorites) holder;

        //cargar los datos
        viewHolderFavorites.loadFavoriteSong(track);

    }

    public Track getItem(Integer position) {
        return favoriteTracks.get(position);
    }

    public void setSongAlbumImage(String songAlbumImage) {
        this.songAlbumImage = songAlbumImage;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    @Override
    public int getItemCount() {
        return favoriteTracks.size();
    }

    @Override
    public void onClick(View v) {

        if(onClickListener != null) {
            onClickListener.onClick(v);
        }

    }

    //View holder - maqueta
    public class ViewHolderFavorites extends RecyclerView.ViewHolder {

        private ImageView favoriteImageResource;
        private TextView favoriteSongTitle;
        private TextView favoriteSongArtist;

        public ViewHolderFavorites(View itemView) {
            super(itemView);
            favoriteImageResource = (ImageView) itemView.findViewById(R.id.favoritesAlbumImage);
            favoriteSongTitle = (TextView) itemView.findViewById(R.id.favritesSongTitle);
            favoriteSongArtist = (TextView) itemView.findViewById(R.id.favoritesSongArtist);
        }

        public void loadFavoriteSong(Track aTrack) {

            GlideApp.with(context).load(aTrack.getTrackAlbumURL()).placeholder(R.drawable.placeholdermusic).into(favoriteImageResource);
            favoriteSongTitle.setText(aTrack.getSongTitle());
            favoriteSongArtist.setText(aTrack.getTrackArtistName());
        }
    }

}
