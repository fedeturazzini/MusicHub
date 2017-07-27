package digitalhouse.android.Controller;

import android.content.Context;

import digitalhouse.android.Model.DAO.DAOInternet.DAOGenreInternet;
import digitalhouse.android.Model.POJO.GenreContainer;
import digitalhouse.android.Util.HTTPConnectionManager;
import digitalhouse.android.Util.ResultListener;

/**
 * Created by fedet on 22/6/2017.
 */

public class GenreController {

    public void obtenerGenre (Context context, final ResultListener<GenreContainer>listener){

        if(HTTPConnectionManager.isNetworkingOnline(context)){
            DAOGenreInternet daoGenreInternet = new DAOGenreInternet();
            daoGenreInternet.obtenerSong(new ResultListener<GenreContainer>() {
                @Override
                public void finish(GenreContainer resultado) {
                    if (resultado != null){
                        //List<Genre>genreList = resultado.getGenreList();
                        listener.finish(resultado);
                    }
                }
            });
        }
    }
}
