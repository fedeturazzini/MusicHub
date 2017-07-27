package digitalhouse.android.View.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import digitalhouse.android.Model.POJO.Album;
import digitalhouse.android.Model.POJO.Artist;
import digitalhouse.android.Model.POJO.Track;
import digitalhouse.android.Util.GlideApp;
import digitalhouse.android.a0317moacns1c_01.R;


public class FragmentSongDetail extends Fragment {

    //Atributos
    private ImageView imageViewSongImage;
    private Context context;
    private String bundleTitle;
    private TextView textViewTitle;
    private TextView textViewArtist;
    private ImageView imageViewDetalle;

    //Constantes
    public static final String KEY_SONG_ID = "keySongID";
    public static final String KEY_SONG_TITLE = "keySongTitle";
    public static final String KEY_SONG_DURATION = "keySongDuration";
    public static final String KEY_SONG_RESOURCE = "keySongResource";
    public static final String KEY_SONG_ARTIST = "keySongArtist";
    public static final String KEY_SONG_IMAGE = "keySongImage";


    public static FragmentSongDetail fabricSong (Track aTrack, String albumImageURL) {

        Bundle bundle = new Bundle();

        Artist artist = aTrack.getArtist();
        String artstName = artist.getName();

        bundle.putString(KEY_SONG_IMAGE, albumImageURL);
        bundle.putString(KEY_SONG_ARTIST, artstName);

        bundle.putInt(KEY_SONG_ID, aTrack.getSongID());
        bundle.putString(KEY_SONG_TITLE, aTrack.getSongTitle());
        bundle.putInt(KEY_SONG_DURATION, aTrack.getSongDuration());
        bundle.putString(KEY_SONG_RESOURCE, aTrack.getSongResource());


        FragmentSongDetail fragmentSong = new FragmentSongDetail();
        fragmentSong.setArguments(bundle);

        return fragmentSong;
    }

    public static FragmentSongDetail fabricFavorite (Track aTrack) {

        Bundle bundle = new Bundle();

        bundle.putString(KEY_SONG_IMAGE, aTrack.getTrackAlbumURL());
        bundle.putString(KEY_SONG_ARTIST, aTrack.getTrackArtistName());

        bundle.putInt(KEY_SONG_ID, aTrack.getSongID());
        bundle.putString(KEY_SONG_TITLE, aTrack.getSongTitle());
        bundle.putInt(KEY_SONG_DURATION, aTrack.getSongDuration());
        bundle.putString(KEY_SONG_RESOURCE, aTrack.getSongResource());


        FragmentSongDetail fragmentSong = new FragmentSongDetail();
        fragmentSong.setArguments(bundle);

        return fragmentSong;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_detail, container, false);

        //inicializar las vistas
        initializeViews(view);

        context = getContext();

        Bundle bundle = getArguments();

        bundleTitle = bundle.getString(KEY_SONG_TITLE);
        String bundleArtist = bundle.getString(KEY_SONG_ARTIST);
        String bundleImageResource = bundle.getString(KEY_SONG_IMAGE);

        textViewTitle.setText(bundleTitle);
        textViewTitle.setTypeface(robotoBold(context));

        textViewArtist.setText(bundleArtist);
        textViewArtist.setTypeface(robotoLight(context));

        GlideApp.with(context).load(bundleImageResource).placeholder(R.drawable.placeholdermusic).into(imageViewSongImage);

        // Cambiar la foto segun el color de la imagen

        imageViewDetalle = (ImageView) view.findViewById(R.id.imageViewDetalle);

        //Inicializo el pallete
        initializePallete(view);

        return view;
    }

    private void initializeViews(View view) {

        //Inicio las vistas aca para que que este mas prolijo
        textViewTitle = (TextView) view.findViewById(R.id.textViewSongTitle);
        imageViewSongImage = (ImageView) view.findViewById(R.id.imageViewDetalle);
        textViewArtist = (TextView) view.findViewById(R.id.textViewSongArtist);

    }

    public void initializePallete(View view) {

        //Guardo en un bitmap la imagen que está en el imageView, con ese bitmap voy a generar una paleta de colores
        Bitmap bitmap = ((BitmapDrawable)imageViewDetalle.getDrawable()).getBitmap();
        //Genero una paleta de colores del bitmap
        Palette palette = Palette.from(bitmap).generate();
        //Consigo un swatch(color) vibrante de la paleta que generé
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        //Puedo pedir diferentes colores de la paleta dependiendo lo que necesite
        //Seteo ese color como background del textview
        TextView textViewCancionDetalle = (TextView) view.findViewById(R.id.textViewSongTitle);
        RelativeLayout frameLayout = (RelativeLayout) view.findViewById(R.id.relativeLayoutMusicDetail);
        //Seteo otro de los colores de la paleta como background del frame
        Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
        if (lightVibrantSwatch != null){
            frameLayout.setBackgroundColor(lightVibrantSwatch.getRgb());
        }


    }

    //retornamos las fuentes necesarias
    public static Typeface robotoLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    }

    public static Typeface robotoBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

}
