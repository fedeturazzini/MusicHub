package digitalhouse.android.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Controller.TracklistController;
import digitalhouse.android.Model.POJO.Track;
import digitalhouse.android.Model.POJO.TrackContainer;
import digitalhouse.android.Util.ResultListener;
import digitalhouse.android.View.Adapters.AdapterListMusic;
import digitalhouse.android.a0317moacns1c_01.R;


public class FragmentListMusic extends Fragment {

    //Atributos
    private NotifyChanges notification;
    private AdapterListMusic adapterListMusic;
    private RecyclerView recycleListMusic;
    private Bundle bundle;
    private String tracklistURL;

    //Constantes
    public static final String TRACKLIST_URL = "trackListUrl";
    public static final String TRACKLIST_ALBUM_IMAGE = "urlimgalbum";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_music, container, false);

        bundle = getArguments();
        tracklistURL  = bundle.getString(TRACKLIST_URL);
        String albumImg = bundle.getString(TRACKLIST_ALBUM_IMAGE);

        List<Track> trackList = new ArrayList<>();
        // Create recycle View
        recycleListMusic = (RecyclerView) view.findViewById(R.id.recycleViewID);
        // Search for layoutmanager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        // Set the manager to the recycle View
        recycleListMusic.setLayoutManager(layoutManager);
        // Create RecycleView Adapter
        adapterListMusic = new AdapterListMusic(getContext(), trackList);
        // Set the adapter to the recycle View
        recycleListMusic.setAdapter(adapterListMusic);

        adapterListMusic.setImgAlbum(albumImg);


        TracklistController tracklistController = new TracklistController();
        tracklistController.obtenerSong(getContext(), new ResultListener<TrackContainer>() {
            @Override
            public void finish(TrackContainer resultado) {
                adapterListMusic.setSongsList(resultado.getTrackList());
                List<Track> DEBUG = resultado.getTrackList();
            }
        }, tracklistURL);

        View.OnClickListener listenerClick = new View.OnClickListener() {
               @Override
            public void onClick(View v) {
                // Completar el OnClick
                   Integer position = recycleListMusic.getChildAdapterPosition(v);
                   Track track = adapterListMusic.getItem(position);
                   ImageView imageMusic = (ImageView) v.findViewById(R.id.listSongImage);
                   notification.sendSong(position, tracklistURL, bundle.getString(TRACKLIST_ALBUM_IMAGE), 1, imageMusic);
            }
        };

        // Set click listener to the adapter
        adapterListMusic.setListener(listenerClick);


        return view;
    }

    public interface NotifyChanges {
        void sendSong(Integer position, String requestUrl, String albumImageURL, Integer id, ImageView imageMusic);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notification = (NotifyChanges) context;
    }
}
