package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "LabelID")
)
public class Label{

    public Label(HashSet<Artist> artists, HashSet<Album> albums) {
        this.artists = artists;
        this.albums = albums;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID labelID;

    @NotNull
    private HashSet<Artist> artists;

    @NotNull
    private HashSet<Album> albums; //TODO: Change to album class name when done

    public UUID getLabelID() {
        return labelID;
    }

    public void setLabelID(UUID labelID) {
        this.labelID = labelID;
    }

    public HashSet<Artist> getArtists() {
        return artists;
    }

    public void setArtists(HashSet<Artist> artists) {
        this.artists = artists;
    }

    public HashSet<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(HashSet<Album> albums) {
        this.albums = albums;
    }
}
