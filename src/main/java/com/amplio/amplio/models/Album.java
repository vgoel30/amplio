package com.amplio.amplio.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
public class Album extends SongCollection {

    @NotNull
    @OneToOne
    private Artist artist;
    @NotNull
    @OneToOne
    private Label label;
    @NotNull
    private SimpleDateFormat date;

    public Album(Artist artist, Label label, SimpleDateFormat date, List<Song> songs, String title) {
        this.artist = artist;
        this.label = label;
        this.date = date;
        setSongs(songs);
        setTitle(title);
    }

    public Album(Artist artist, Label label) {
        this.artist = artist;
        this.label = label;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }
}
