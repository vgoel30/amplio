package com.amplio.amplio.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Playlist extends SongCollection {
    private String image;

    @NotNull
    @OneToOne
    private User owner;

    @NotNull
    private boolean isPublic;


    public void addSong(Song song) {
        getSongs().add(song);
    }

    public void removeSong(Song song) {
        getSongs().remove(song);
    }
}
