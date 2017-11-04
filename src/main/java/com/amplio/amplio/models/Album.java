package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "AlbumID")
)
public class Album {

    public Album(Artist artist, Label label, SimpleDateFormat date) {
        this.artist = artist;
        this.label = label;
        this.date = date;
    }

    public Album(Artist artist, Label label) {
        this.artist = artist;
        this.label = label;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID albumID;

    @NotNull
    private Artist artist;

    @NotNull
    private Label label;

    @NotNull
    private SimpleDateFormat date;

    public UUID getAlbumID() {
        return albumID;
    }

    public void setAlbumID(UUID albumID) {
        this.albumID = albumID;
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
