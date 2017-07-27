package digitalhouse.android.View.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.Model.POJO.Track;
import digitalhouse.android.View.Fragments.FragmentSongDetail;

public class AdapterViewPagerSong extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Track> trackList;
    private String albumImageURL = "";

    public AdapterViewPagerSong(FragmentManager fm, List<Track> listTracks, String albumImageURL) {
        super(fm);
        this.albumImageURL = albumImageURL;
        trackList = listTracks;
        for (Track track : listTracks){
            FragmentSongDetail fragmentSongDetail = FragmentSongDetail.fabricSong(track, albumImageURL);
            fragmentList.add(fragmentSongDetail);
        }
    }

    public AdapterViewPagerSong(FragmentManager fm, List<Track> listTracks) {
        super(fm);
        this.albumImageURL = albumImageURL;
        trackList = listTracks;
        for (Track track : listTracks){
            FragmentSongDetail fragmentSongDetail = FragmentSongDetail.fabricFavorite(track);
            fragmentList.add(fragmentSongDetail);
        }
    }

    public void setSongsList(List<Track> songsList) {
        this.trackList = songsList;
        for (Track track : songsList){
            FragmentSongDetail fragmentSongDetail = FragmentSongDetail.fabricSong(track, albumImageURL);
            fragmentList.add(fragmentSongDetail);
        }
        notifyDataSetChanged();
    }

    public Track getSongItem(Integer position){
        return trackList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
