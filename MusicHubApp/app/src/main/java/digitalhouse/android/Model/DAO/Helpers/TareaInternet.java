package digitalhouse.android.Model.DAO.Helpers;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import digitalhouse.android.Util.DAOException;
import digitalhouse.android.Util.HTTPConnectionManager;
import digitalhouse.android.Util.ResultListener;


public class TareaInternet <T> extends AsyncTask<String, Void, T> {

    private ResultListener<T> listenerControllerCalamardo;
    private Class <T> claseContenedora;

    public TareaInternet(ResultListener<T> listenerControllerCalamardo, Class<T> claseContenedora) {
        this.listenerControllerCalamardo = listenerControllerCalamardo;
        this.claseContenedora = claseContenedora;
    }

    @Override
    protected T doInBackground(String[] params) {
        String url = params[0];
        T objetoContenedor = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        HTTPConnectionManager httpConnectionManager = null;

        try {
            //Pedir a Internet json
            httpConnectionManager = new HTTPConnectionManager();
            inputStream = httpConnectionManager.getRequestStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Pedir a GSON que parsee el json
            Gson gson = new Gson();
            objetoContenedor = gson.fromJson(bufferedReader, claseContenedora);

            // devuelvo el objeto contenedor
            return objetoContenedor;
        } catch (DAOException e) {
            // Cierro el buffer
            e.printStackTrace();
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                } else if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e2) {
                e2.printStackTrace();
            }

            // Cierro la conexion
            httpConnectionManager.closeConnection();
        }

        return objetoContenedor;
    }


    @Override
    protected void onPostExecute (T resultado) {
        // aviso al Controller(Calamardo) que el objetoContainer ya esta listo
        listenerControllerCalamardo.finish(resultado);
    }
}