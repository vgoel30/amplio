package com.amplio.amplio.models;
import java.util.List;

public class Statistics {
    private List<Artist> topArtists;
    private List<Song> topSongs;

    public Statistics(){
        this.topArtists = null;
        this.topSongs = null;
    }

    public void addTopArtist(Artist artist){
        this.topArtists.add(artist);
    }

    public void addTopSong(Song song){
        this.topSongs.add(song);

    }

    public void delTopArtist(Artist artist){
        this.topArtists.remove(artist);
    }

    public void delTopSong(Song song){
        this.topSongs.remove(song);

    }

}

