package digitalhouse.android.View.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Model.DAO.DAOSDatabase.DAOFavoritesDatabase;
import digitalhouse.android.Model.POJO.Track;
import digitalhouse.android.View.Adapters.AdapterFavorites;
import digitalhouse.android.a0317moacns1c_01.R;


public class FragmentFavorites extends Fragment {

    private List<Track> favoriteSongsList = new ArrayList<>();
    private RecyclerView recyclerViewFavrites;
    private AdapterFavorites adapterFavorites;
    private NotifyChangesFavorites notifyChanges;
    private DAOFavoritesDatabase daoFavoritesDatabase;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        daoFavoritesDatabase = new DAOFavoritesDatabase(getContext());
        firebaseAuth = FirebaseAuth.getInstance();

        favoriteSongsList = daoFavoritesDatabase.getFavoriteSongsList();

        //Si la lista de favoritos se encuentra vacia va a mostrar un text view
        TextView nothingFound = (TextView) view.findViewById(R.id.nothingFound);
        if (favoriteSongsList.size() == 0) {
            nothingFound.setText("No favorites here!");
        }

        //LLamando al recyclerViewFavrites del layout
        recyclerViewFavrites = (RecyclerView) view.findViewById(R.id.recyclerViewFavorites);

        //Layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFavrites.setLayoutManager(layoutManager);

        //instanciar el adapter
        adapterFavorites = new AdapterFavorites(favoriteSongsList, container.getContext());
        if (favoriteSongsList != null){
            if (firebaseAuth.getCurrentUser() != null) {
                recyclerViewFavrites.setAdapter(adapterFavorites);
            } else {
                nothingFound.setText("Please login to add songs");
            }
        }


        //setear el adapter a un nuevo onCLickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = recyclerViewFavrites.getChildAdapterPosition(v);
                notifyChanges.sendPosition(position, 2);
            }
        };

        adapterFavorites.setOnClickListener(listener);

        return view;
    }

    public interface NotifyChangesFavorites {
        void sendPosition (Integer position, Integer fragmentID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        notifyChanges = (NotifyChangesFavorites) getContext();
    }
}
