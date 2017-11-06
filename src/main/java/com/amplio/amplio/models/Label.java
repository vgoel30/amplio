package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
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
    @OneToMany
    private Set<Artist> artists;

    @NotNull
    @OneToMany
    private Set<Album> albums;

    public UUID getLabelID() {
        return labelID;
    }

    public void setLabelID(UUID labelID) {
        this.labelID = labelID;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(HashSet<Artist> artists) {
        this.artists = artists;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(HashSet<Album> albums) {
        this.albums = albums;
    }
}
