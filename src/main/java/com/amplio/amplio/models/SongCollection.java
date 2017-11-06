package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SongCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID collectionID;

    @NotNull
    private String title;
    @NotNull
    private List<Song> songs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public UUID getID() {
        return collectionID;
    }

    public void setID(UUID collectionID) {
        this.collectionID = collectionID;
    }
}
