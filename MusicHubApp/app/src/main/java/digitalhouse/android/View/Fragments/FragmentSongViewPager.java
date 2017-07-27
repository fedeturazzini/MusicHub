package digitalhouse.android.View.Fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Controller.TracklistController;
import digitalhouse.android.Model.DAO.Helpers.MediaPlayerHelper;
import digitalhouse.android.Model.DAO.DAOSDatabase.DAOFavoritesDatabase;
import digitalhouse.android.Model.POJO.Track;
import digitalhouse.android.Model.POJO.TrackContainer;
import digitalhouse.android.Util.ResultListener;
import digitalhouse.android.View.Activities.ActivityLogin;
import digitalhouse.android.View.Adapters.AdapterViewPagerSong;
import digitalhouse.android.a0317moacns1c_01.R;


public class FragmentSongViewPager extends Fragment {

    //Atributos de vista
    private ViewPager viewPager;
    private View view;
    private Snackbar snackbar;
    private ImageView botonPlay;
    private SeekBar seekBar;
    private TextView textViewCurrentPosition;
    private TextView textViewTotalLength;
    private View parentLayout;
    private ImageView btnFavorite;

    //De datos
    private  Bundle bundle;

    //De Database
    private DAOFavoritesDatabase daoFavoritesDatabase;

    //Relacionados con mediaPlayer y servicios
    private String stringAudioLink;
    private Intent serviceIntent;
    private Boolean boolMusicPlaying = false;
    private Track actualTrack;
    private List<Track> songsList = new ArrayList<>();
    private TextView textViewDetailPlayingFrom;
    private Boolean serviceBound = false;
    private FirebaseAuth firebaseAuth;
    private Handler handler;
    private Handler handler1;

    //Adapters
    AdapterViewPagerSong adapterViewPagerSong;

    //Constantes
    public static final String KEY_POSITION = "keyPosition";
    public static final String KEY_URL_REQUEST = "urlRequest";
    public static final String KEY_ALBUM_IMAGE_URL = "albumImageURL";
    public static final String KEY_FRAGMENT_ID = "fragmentID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_song_view_pager, container, false);

        //Instancio Firebase auto
        firebaseAuth = FirebaseAuth.getInstance();

        //Inicializo la base de datos
        daoFavoritesDatabase = new DAOFavoritesDatabase(getContext());

        //Iniciar las vistas correspondientes
        initializeViews();

        bundle = getArguments();

        //Texto que muestra desde donde se esta reproduciendo la musica
        if (bundle.getInt(KEY_FRAGMENT_ID) == 1) {
            textViewDetailPlayingFrom.setText("Reproduciendo de Album");
        } else if (bundle.getInt(KEY_FRAGMENT_ID) == 2) {
            textViewDetailPlayingFrom.setText("Reproduciendo de Favoritos");
        }

        //Si el ID es 1, significa que el bundle viene desde fragmentListMusic
        if (bundle.getInt(KEY_FRAGMENT_ID) == 1) {

            adapterViewPagerSong = new AdapterViewPagerSong(getActivity().getSupportFragmentManager(), songsList, bundle.getString(KEY_ALBUM_IMAGE_URL));
            viewPager.setAdapter(adapterViewPagerSong);

            //Seteo de adapter
            viewPager.setCurrentItem(bundle.getInt(KEY_POSITION), false);

            //llamo a un song controller - JSON Hardcodeado hasta que tengamos el definitivo
            TracklistController tracklistController = new TracklistController();
            tracklistController.obtenerSong(getContext(), new ResultListener<TrackContainer>() {
                @Override
                public void finish(TrackContainer resultado) {
                    adapterViewPagerSong.setSongsList(resultado.getTrackList());
                    viewPager.setCurrentItem(bundle.getInt(KEY_POSITION), false);

                }
            }, bundle.getString(KEY_URL_REQUEST));

        } else if (bundle.getInt(KEY_FRAGMENT_ID) == 2) {

            songsList = daoFavoritesDatabase.getFavoriteSongsList();

            adapterViewPagerSong = new AdapterViewPagerSong(getActivity().getSupportFragmentManager(), songsList);
            viewPager.setAdapter(adapterViewPagerSong);

            //Seteo de adapter
            viewPager.setCurrentItem(bundle.getInt(KEY_POSITION), false);
        }

        //Listener de btn favorito
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth.getCurrentUser() != null) {

                    if (daoFavoritesDatabase.isSongInDb(actualTrack.getSongID())) {

                        daoFavoritesDatabase.deleteFromDatabase(actualTrack.getSongID());

                        btnFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);

                        Snackbar.make(parentLayout, "Song deleted from favorites", Snackbar.LENGTH_SHORT).show();

                        if (bundle.getInt(KEY_FRAGMENT_ID) == 2) {
                            getFragmentManager().popBackStack();
                        }

                    } else {

                        actualTrack.setTrackAlbumURL(bundle.getString(KEY_ALBUM_IMAGE_URL));

                        daoFavoritesDatabase.addSong(actualTrack);

                        btnFavorite.setImageResource(R.drawable.favorite_white);

                        Snackbar.make(parentLayout, "Song added to favorites!", Snackbar.LENGTH_SHORT).show();

                    }

                } else {

                    Intent intent = new Intent(getActivity(), ActivityLogin.class);
                    startActivityForResult(intent, 1);

                }

            }
        });


        //Listener de scrrol de viewPgaer
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                actualTrack = ((AdapterViewPagerSong)viewPager.getAdapter()).getSongItem(position);



                if (MediaPlayerHelper.mediaPlayerIsNull()){
                    if (MediaPlayerHelper.getMediaPlayer().isPlaying()) {

                        MediaPlayerHelper.play(actualTrack.getSongResource());
                        botonPlay.setImageResource(R.drawable.pause_white);

                    } else {
                        MediaPlayerHelper.play(actualTrack.getSongResource());
                        botonPlay.setImageResource(R.drawable.pause_white);

                    }

                } else {
                    MediaPlayerHelper.play(actualTrack.getSongResource());
                    botonPlay.setImageResource(R.drawable.pause_white);

                }

                setCurrentPosition();


                //Configurar el largo total
                textViewTotalLength.setText(secondsFormatter(actualTrack.getSongDuration()));

                //BOTON DE FAVORITOS
                daoFavoritesDatabase = new DAOFavoritesDatabase(getContext());
                if (daoFavoritesDatabase.isSongInDb(actualTrack.getSongID())) {
                    btnFavorite.setImageResource(R.drawable.favorite_white);
                } else {
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                }
            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Boton siguiente
        ImageView buttonNext = (ImageView) view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true);
            }
        });

        //Boton atras
        ImageView imageViewPrevious = (ImageView) view.findViewById(R.id.buttonPrevious);
        imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(-1), true);
            }
        });

        //Button play
        botonPlay = (ImageView) view.findViewById(R.id.playButtonID);
        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //asi se arranca el servicio con play(para ponerlo adentro de un listener)
                if (MediaPlayerHelper.mediaPlayerIsNull()) {
                    botonPlay.setImageResource(R.drawable.pause_white);
                    setCurrentPosition();
                    MediaPlayerHelper.play(actualTrack.getSongResource());
                } else {
                    if (!MediaPlayerHelper.getMediaPlayer().isPlaying()) {
                        botonPlay.setImageResource(R.drawable.pause_white);
                        MediaPlayerHelper.resume();
                    } else {
                        if (MediaPlayerHelper.getMediaPlayer().isPlaying()) {
                            botonPlay.setImageResource(R.drawable.play_white);
                            MediaPlayerHelper.pause();
                        }
                    }
                }

            }

        });

        // SHARE BUTTON FACEBOOK
        ImageView shareButton = (ImageView) view.findViewById(R.id.buttonShare);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos un share de tipo ACTION_SENT
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                //Indicamos que voy a compartir texto
                share.setType("text/plain");
                //Le agrego un título
                share.putExtra(Intent.EXTRA_SUBJECT, "Compartir");
                //Le agrego el texto a compartir (Puede ser un link tambien)
                share.putExtra(Intent.EXTRA_TEXT, "MusicHub tiene la mejor musica! #MusicHub #Music #Share");
                //Hacemos un start para que comparta el contenido.
                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });



        return view;
    }

    private int getItem(int i) {

        return viewPager.getCurrentItem() + i;
    }


    //Inicio las vistas aca para que que este mas prolijo
    private void initializeViews() {
        seekBar = (SeekBar) view.findViewById(R.id.songDetailSeekBar);
        textViewCurrentPosition = (TextView) view.findViewById(R.id.songTimeActualProgress);
        textViewTotalLength = (TextView) view.findViewById(R.id.songTimerMediaPlayerLength);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerSong);
        parentLayout = view.findViewById(R.id.parentLayout);
        btnFavorite = (ImageView) view.findViewById(R.id.buttonAddFavourite);
        textViewDetailPlayingFrom = (TextView) view.findViewById(R.id.textViewDetalleReproduciendo);
    }



    //Recibe el tiempo en segundos y los devuelve con el formato adecuado
    private String secondsFormatter(Integer totalDurationInS) {

        Integer hours = totalDurationInS / 3600;
        Integer minutes = (totalDurationInS - (3600 * hours)) / 60;
        Integer seconds =  totalDurationInS - (hours * 3600 + minutes * 60);

        String durationFormatted = "";

        if(seconds < 10) {
            durationFormatted = minutes + ":0" + seconds;
        } else {
            durationFormatted = minutes + ":" + seconds;
        }

        return durationFormatted;
    }

    //Recibe el tiempo en milisegundos y los devuelve con el formato adecuado
    private String milisecondsFormatter(Integer totalDurationInMS) {

        Integer seconds = (totalDurationInMS / 1000) % 60;
        Integer minutes = ((totalDurationInMS / 1000) / 60) % 60;
        String durationFormatted = "";

        if(seconds < 10) {
            durationFormatted = minutes + ":0" + seconds;
        } else {
            durationFormatted = minutes + ":" + seconds;
        }

        return durationFormatted;
    }

    public void setCurrentPosition() {
        //Seteo de maximo al seekbar
        seekBar.setMax((Integer) MediaPlayerHelper.getMediaPlayer().getDuration() / 1000);

        handler = new Handler();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                textViewCurrentPosition.setText(milisecondsFormatter(MediaPlayerHelper.getMediaPlayer().getCurrentPosition()));

                seekBar.setMax(30);

                Integer currentPosition = MediaPlayerHelper.getMediaPlayer().getCurrentPosition();
                seekBar.setProgress(currentPosition / 1000);

                handler.postDelayed(this, 1000);


            }
        });


    }

    //Busca la posicion donde se toco el seekbar
    private void handleSeekBar() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                MediaPlayerHelper.seekTo(progress * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


}
