package digitalhouse.android.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import digitalhouse.android.Model.POJO.Track;
import digitalhouse.android.Util.GlideApp;
import digitalhouse.android.a0317moacns1c_01.R;

/**
 * Created by fedet on 26/5/2017.
 */

public class AdapterListMusic extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<Track> songsList;
    private View.OnClickListener myListener;
    private String imgAlbum;

    public AdapterListMusic(Context context, List<Track> songsList) {
        this.context = context;
        this.songsList = songsList;
    }

    public void setImgAlbum(String imgAlbum) {
        this.imgAlbum = imgAlbum;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Create inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate
        View view = inflater.inflate(R.layout.layout_detail_song, parent, false);
        // Set in the View the onclick listener
        view.setOnClickListener(this);

        // Instance ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public void setSongsList(List<Track> songsList) {
        this.songsList = songsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Track track = songsList.get(position);
        ViewHolder myViewHolder = (ViewHolder) holder;

        myViewHolder.asignTransitionName(position);
        myViewHolder.loadData(track);

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public Track getItem (Integer position){
        return songsList.get(position);
    }

    @Override
    public void onClick(View v) {
        if (myListener != null){
            myListener.onClick(v);
        }
    }

    public void setListener (View.OnClickListener listener){
        myListener = listener;
    }

    // Create class ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{

        // Falta declarar los atributos
        private ImageView imageViewSongImage;
        private TextView textViewSongTitle;
        private TextView textViewSongArtist;


        public ViewHolder(View itemView) {
            super(itemView);
            imageViewSongImage = (ImageView) itemView.findViewById(R.id.listSongImage);
            textViewSongTitle = (TextView) itemView.findViewById(R.id.listSongTitle);
            textViewSongArtist = (TextView) itemView.findViewById(R.id.listSongDescription);
        }

        // Falta cargar los datos del detalle de las canciones
        public void loadData(Track track){
            GlideApp.with(context).load(imgAlbum).placeholder(R.drawable.placeholdermusic).into(imageViewSongImage);

            //Esto no permite que el titulo se pase de 25 caracteres
            if (track.getSongTitle().length() < 25) {
                textViewSongTitle.setText(track.getSongTitle());
            } else {
                String aux = track.getSongTitle().substring(0, 25);
                textViewSongTitle.setText(aux + "...");
            }

            textViewSongArtist.setText(track.getArtist().getName());

            textViewSongTitle.setTypeface(robotoBold(context));
            textViewSongArtist.setTypeface(robotoLight(context));
        }

        public void asignTransitionName(Integer position){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageViewSongImage.setTransitionName( "Transition" + position);
            }
        }
    }

    //devuelven las fuentes deseadas
    public static Typeface robotoLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    }

    public static Typeface robotoBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }


}
