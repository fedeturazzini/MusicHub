package digitalhouse.android.View.Fragments;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import digitalhouse.android.Controller.AlbumController;
import digitalhouse.android.Model.POJO.Album;
import digitalhouse.android.Model.POJO.AlbumContainer;
import digitalhouse.android.Util.ResultListener;
import digitalhouse.android.View.Adapters.AdapterAlbumList;
import digitalhouse.android.a0317moacns1c_01.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAlbumList extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    //Constantes
    public static final String KEY_GENRE_ID = "keyGenreID";
    public static final String KEY_GENRE_NAME = "keyGenreName";
    public static final String KEY_GENERE_PICTURE_URL = "keyGenrePictureUrl";

    //Atributos
    private AdapterAlbumList adapterAlbumList;
    private ActivityChangeNotificator activityChangeNotificator;
    private Album album;
    private Integer genreId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        genreId = bundle.getInt(KEY_GENRE_ID);
        String genreName = bundle.getString(KEY_GENRE_NAME);
        String genreImageUrl = bundle.getString(KEY_GENERE_PICTURE_URL);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);

        final RecyclerView recyclerViewAlbums = (RecyclerView) view.findViewById(R.id.recyclerViewAlbumList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 3);
        recyclerViewAlbums.setLayoutManager(layoutManager);

        //Adapter
        adapterAlbumList = new AdapterAlbumList(getContext());
        recyclerViewAlbums.setAdapter(adapterAlbumList);

        // Controller
        AlbumController albumController = new AlbumController();
        albumController.obtainAlbum(getContext(), new ResultListener<AlbumContainer>() {
            @Override
            public void finish(AlbumContainer resultado) {
                adapterAlbumList.setAlbumList(resultado.getAlbumList());
            }
        }, genreId);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = recyclerViewAlbums.getChildAdapterPosition(v);
                album = adapterAlbumList.getItem(position);


                // save rect of view in screen coordinates
                final Rect viewRect = new Rect();
                v.getGlobalVisibleRect(viewRect);

                // create Explode transition with epicenter
                Transition explode = new Explode();
                explode.setEpicenterCallback(new Transition.EpicenterCallback() {
                    @Override
                    public Rect onGetEpicenter(Transition transition) {
                        return viewRect;
                    }
                });
                explode.setDuration(370);
                explode.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        activityChangeNotificator.notifyChanges(album);
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {

                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                });

                TransitionManager.beginDelayedTransition(recyclerViewAlbums, explode);

                // remove all views from Recycler View
                recyclerViewAlbums.setAdapter(null);


            }
        };

        adapterAlbumList.setOnClickListener(onClickListener);

        return view;
    }

    // Refresh swipe
    @Override
    public void onRefresh() {
        AlbumController albumController = new AlbumController();
        albumController.obtainAlbum(getContext(), new ResultListener<AlbumContainer>() {
            @Override
            public void finish(AlbumContainer resultado) {
                adapterAlbumList.setAlbumList(resultado.getAlbumList());
            }
        }, genreId);
    }

    public interface ActivityChangeNotificator {
        public void notifyChanges(Album album);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activityChangeNotificator = (ActivityChangeNotificator) context;
    }

}
