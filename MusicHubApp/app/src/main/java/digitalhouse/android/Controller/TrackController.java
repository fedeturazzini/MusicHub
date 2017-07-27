package digitalhouse.android.Controller;

import android.content.Context;

import digitalhouse.android.Model.POJO.TrackContainer;
import digitalhouse.android.Util.HTTPConnectionManager;
import digitalhouse.android.Util.ResultListener;
import digitalhouse.android.Model.DAO.DAOInternet.DAOMusicInternet;

/**
 * Created by gastonyelmini on 10/6/17.
 */

public class TrackController {

    //metodo para pedir la lista de restaurants al DAO
    public void obtenerSong (Context context, final ResultListener<TrackContainer> listener) {

        if (HTTPConnectionManager.isNetworkingOnline(context)){
            DAOMusicInternet DAOMusicInternet = new DAOMusicInternet();
            DAOMusicInternet.obtenerSong(new ResultListener<TrackContainer>() {
                @Override
                public void finish(TrackContainer resultado) {
                    if (resultado != null){
//                        List<Track> tracks = container.getTrackList();
                        listener.finish(resultado);
                    }
                }
            });
        } else {
//            //Lamo a un DAO
//            DAOMusicFile daoMusicFile = new DAOMusicFile();
//            //Le pido la lista de canciones
//            List<Track> trackList = daoMusicFile.getSongListFromFile(context);
        }
//        //devuelvo la lista
//        return songList;
    }

}
