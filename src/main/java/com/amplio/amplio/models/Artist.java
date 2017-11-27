package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID artistID;

  @NotNull
  private String name;
  private String bibliography;

  @NotNull
  @OneToMany
  private Set<Album> albums;

  @ManyToMany(cascade = CascadeType.ALL)
  private Set<Concert> concerts;

  @OneToMany
  private List<Song> deleteRequests;

  @OneToMany
  private List<Song> uploadRequests;

  @NotNull
  @ManyToOne
  private Label label;

  public Artist(String name, String bibliography, Set<Album> albums, Set<Concert> concerts, Label label) {
    this.name = name;
    this.bibliography = bibliography;
    this.albums = albums;
    this.concerts = concerts;
    this.label = label;
  }

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

  public Set<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(HashSet<Album> albums) {
    this.albums = albums;
  }

  @JoinTable(
      name = "artist_concert",
      joinColumns = @JoinColumn(name = "artist_id"),
      inverseJoinColumns = @JoinColumn(name = "concert_id")
  )
  public Set<Concert> getConcerts() {
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

  public List<Song> getDeleteRequests() {
    return deleteRequests;
  }

  public void setDeleteRequests(List<Song> deleteRequests) {
    this.deleteRequests = deleteRequests;
  }

  public List<Song> getUploadRequests() {
    return uploadRequests;
  }

  public void setUploadRequests(List<Song> uploadRequests) {
    this.uploadRequests = uploadRequests;
  }

}
