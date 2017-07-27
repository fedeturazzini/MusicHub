package digitalhouse.android.View.Fragments;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Controller.GenreController;
import digitalhouse.android.Model.POJO.Genre;
import digitalhouse.android.Model.POJO.GenreContainer;
import digitalhouse.android.Util.ResultListener;
import digitalhouse.android.View.Adapters.AdapterGenreList;
import digitalhouse.android.a0317moacns1c_01.R;


public class FragmentGenreList extends Fragment {

    private AdapterGenreList adapterGenreList;
    private ActivityNotificator activityNotificator;
    private Genre genre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genre_list, container, false);

        final RecyclerView recyclerViewGenreList = (RecyclerView) view.findViewById(R.id.recyclerViewGenreList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerViewGenreList.setLayoutManager(layoutManager);
        List <Genre> genreList = new ArrayList<>();

        //Adapter
        adapterGenreList = new AdapterGenreList(genreList, getContext());
        recyclerViewGenreList.setAdapter(adapterGenreList);
        recyclerViewGenreList.setLayoutManager(layoutManager);

        // Controller
        GenreController genreController = new GenreController();
        genreController.obtenerGenre(getContext(), new ResultListener<GenreContainer>() {
            @Override
            public void finish(GenreContainer resultado) {
                adapterGenreList.setGenreList(resultado.getGenreList());
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer position = recyclerViewGenreList.getChildAdapterPosition(v);
                genre = adapterGenreList.getGenreItem(position);

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
                        activityNotificator.getGenre(genre);
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

                TransitionManager.beginDelayedTransition(recyclerViewGenreList, explode);

                // remove all views from Recycler View
                recyclerViewGenreList.setAdapter(null);

            }
        };

        adapterGenreList.setClickListener(onClickListener);

        return view;
    }

    public interface ActivityNotificator {
        public void getGenre(Genre genre);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activityNotificator = (ActivityNotificator) context;
    }
}
