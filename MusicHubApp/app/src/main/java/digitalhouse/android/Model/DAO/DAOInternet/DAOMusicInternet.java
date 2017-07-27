package digitalhouse.android.Model.DAO.DAOInternet;

import digitalhouse.android.Model.DAO.Helpers.DeezerHelper;
import digitalhouse.android.Model.DAO.Helpers.TareaInternet;
import digitalhouse.android.Model.POJO.TrackContainer;
import digitalhouse.android.Util.ResultListener;

/**
 * Created by fedet on 13/6/2017.
 */

public class DAOMusicInternet {

    public void obtenerSong (ResultListener<TrackContainer> listener){
        TareaInternet<TrackContainer> taskSong = new TareaInternet(listener, TrackContainer.class);
        String url = DeezerHelper.chartTracksPorGenero(0);
        taskSong.execute(url);
        //taskSong.execute("https://api.myjson.com/bins/15grbb");
    }
}
