package digitalhouse.android.Model.DAO.DAOInternet;

import digitalhouse.android.Model.DAO.Helpers.DeezerHelper;
import digitalhouse.android.Model.DAO.Helpers.TareaInternet;
import digitalhouse.android.Model.POJO.GenreContainer;
import digitalhouse.android.Util.ResultListener;

/**
 * Created by fedet on 13/6/2017.
 */

public class DAOGenreInternet {

    public void obtenerSong (ResultListener<GenreContainer> listener){
        TareaInternet<GenreContainer> taskGenre = new TareaInternet(listener, GenreContainer.class);
        String url = DeezerHelper.listaDeGeneros();
        taskGenre.execute(url);
    }
}
