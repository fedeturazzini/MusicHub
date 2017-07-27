package digitalhouse.android.Model.DAO.DAOSDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Model.DAO.Helpers.DatabaseHelper;
import digitalhouse.android.Model.POJO.Track;

/**
 * Created by gaston on 22/06/17.
 */

public class DAOFavoritesDatabase extends DatabaseHelper {

    public static final String TABLE_NAME = "favoritesTable";
    public static final String SONG_ID = "SongId";
    public static final String SONG_TITLE = "songTitle";
    public static final String SONG_DURATION = "songDuration";
    public static final String SONG_RESOURCE = "songResource";
    public static final String SONG_ARTIST = "songArtist";
    public static final String SONG_ALBUM_IMAGE = "songAlbumImage";

    private Boolean songAdded;
    private List<Track> favoriteSongsList = new ArrayList<>();

    public DAOFavoritesDatabase(Context context) {
        super(context);
    }

    public Boolean addSong(Track song) {

        if(isSongInDb(song.getSongID()).equals(false)) {

            SQLiteDatabase database = getWritableDatabase();

            //Mostrarle a la base de datos donde queres que ponga las cosas
            ContentValues contentValues = new ContentValues();

            contentValues.put(SONG_ID, song.getSongID());
            contentValues.put(SONG_TITLE, song.getSongTitle());
            contentValues.put(SONG_DURATION, song.getSongDuration());
            contentValues.put(SONG_RESOURCE, song.getSongResource());

            if (song.getArtist() != null) {
                contentValues.put(SONG_ARTIST, song.getArtist().getName());
            } else {
                contentValues.put(SONG_ARTIST, song.getTrackArtistName());
            }

            contentValues.put(SONG_ALBUM_IMAGE, song.getTrackAlbumURL());

            database.insert(TABLE_NAME, null,contentValues);

            songAdded = true;

            //IMPORTANTE - cerrar la base de datos
            database.close();

        } else {

            songAdded = false;

        }

        return songAdded;

    }

    public Boolean isSongInDb(Integer id) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + SONG_ID + " = " + id ;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        Boolean resultadorCursor = cursor.moveToNext();

        database.close();
        cursor.close();

        return resultadorCursor;

    }

    public List<Track> getFavoriteSongsList() {

        String query = "SELECT * FROM " + TABLE_NAME ;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Integer songID = cursor.getInt(cursor.getColumnIndex(SONG_ID));
            String songTitle = cursor.getString(cursor.getColumnIndex(SONG_TITLE));
            Integer songDuration = cursor.getInt(cursor.getColumnIndex(SONG_DURATION));
            String songURL = cursor.getString(cursor.getColumnIndex(SONG_RESOURCE));

            Track song = new Track(songID, songTitle, songDuration, songURL, null, null);

            song.setTrackArtistName(cursor.getString(cursor.getColumnIndex(SONG_ARTIST)));
            song.setTrackAlbumURL(cursor.getString(cursor.getColumnIndex(SONG_ALBUM_IMAGE)));
            favoriteSongsList.add(song);
        }

        database.close();
        cursor.close();

        return favoriteSongsList;

    }

    public void deleteFromDatabase(Integer id) {

        String query = SONG_ID + " = " + "'" +id+"'";

        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, query, null);

        database.close();

    }

}
