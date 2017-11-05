package com.amplio.amplio.models;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
public class Statistics {
    @OneToMany
    private List<Artist> topArtists;
    @OneToMany
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

