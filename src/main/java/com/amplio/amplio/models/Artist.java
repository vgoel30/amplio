package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "ArtistID")
)
public class Artist {

    public Artist(String name, String bibliography, HashSet<Album> albums, HashSet<Concert> concerts, Label label) {
        this.name = name;
        this.bibliography = bibliography;
        this.albums = albums;
        this.concerts = concerts;
        this.label = label;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID artistID;

    @NotNull
    private String name;

    private String bibliography;

    @NotNull
    @OneToMany
    private  HashSet<Album> albums;

    private HashSet<Concert> concerts;

    @NotNull
    @ManyToOne
    private Label label;

    public UUID getArtistID() {
        return artistID;
    }

    public void setArtistID(UUID artistID) {
        this.artistID = artistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public HashSet<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(HashSet<Album> albums) {
        this.albums = albums;
    }

    public HashSet<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(HashSet<Concert> concerts) {
        this.concerts = concerts;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
