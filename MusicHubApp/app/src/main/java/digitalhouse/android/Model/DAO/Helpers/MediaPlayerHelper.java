package digitalhouse.android.Model.DAO.Helpers;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;

import digitalhouse.android.Model.POJO.Track;


public class MediaPlayerHelper {

    private static int length;
    private static ArrayList<Track> tracks;
    private static Integer position;
    private static MediaPlayer mediaPlayer;


    public static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
        return mediaPlayer;
    }

    public static void setTracks(ArrayList<Track> tracks) {
        MediaPlayerHelper.tracks = tracks;
    }

    public static void setPosition(Integer position) {
        MediaPlayerHelper.position = position;
    }

//    public static String getTrackname(){
//        return tracks.get(position).getSongTitle();
//    }
//
//    public static String getAlbumTitle(){
//        return tracks.get(position).getAlbumTitle();
//    }

    public static void play(String url) {
        MediaPlayer mediaPlayer = getMediaPlayer();
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }


    public static void playResurce(){

    }

    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }
    }

    public static Boolean mediaPlayerIsNull() {
        return mediaPlayer == null;
    }

    public static void resetMediaPlayer() {
        mediaPlayer.release();
    }

    public static void stopMediaPlayer() {
        mediaPlayer.reset();
    }

    public static void seekTo(Integer position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

}
