package com.amplio.amplio.models;
import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID statsID;
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

