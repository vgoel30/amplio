package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Label {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID labelID;

  @NotNull
  private Set<Artist> artists;

  @NotNull
  private Set<Album> albums;

  public Label(HashSet<Artist> artists, HashSet<Album> albums) {
    this.artists = artists;
    this.albums = albums;
  }

  public UUID getLabelID() {
    return labelID;
  }

  public void setLabelID(UUID labelID) {
    this.labelID = labelID;
  }

  @OneToMany
  public Set<Artist> getArtists() {
    return artists;
  }

  public void setArtists(HashSet<Artist> artists) {
    this.artists = artists;
  }

  @OneToMany
  public Set<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(HashSet<Album> albums) {
    this.albums = albums;
  }
}
