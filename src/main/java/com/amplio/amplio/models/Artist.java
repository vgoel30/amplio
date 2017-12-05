package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer artistID;

  @NotNull
  private String name;
  private String bibliography;


  @ManyToMany(cascade = CascadeType.ALL)
  private Set<Concert> concerts;

  @OneToMany
  private List<Song> deleteRequests;

  @OneToMany
  private List<Song> uploadRequests;

  public Artist() {
  }

  public Artist(String name, String bibliography, Set<Concert> concerts) {
    this.name = name;
    this.bibliography = bibliography;
    this.concerts = concerts;
  }

  public Integer getArtistID() {
    return artistID;
  }

  public void setArtistID(Integer artistID) {
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
