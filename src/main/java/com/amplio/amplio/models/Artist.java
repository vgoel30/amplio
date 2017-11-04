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
    private  HashSet<Album> albums; //TODO: Use Album class

    private  HashSet<Concert> concerts; //TODO: Use Concert class

    @NotNull
    private  Label label;//TODO: Use Label class
}
