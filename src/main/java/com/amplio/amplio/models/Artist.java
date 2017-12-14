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
  private Integer id;

  @NotNull
  private String name;
  private String bibliography;


  @ManyToMany(cascade = CascadeType.ALL)
  private Set<Concert> concerts;

  @OneToMany
  private List<Song> deleteRequests;

  @OneToMany
  private List<Song> uploadRequests;

  private String image;

  @NotNull
  @Enumerated(EnumType.STRING)
  private GenreEnum genre;

  public Artist() {
  }

  public Artist(String name, String bibliography, Set<Concert> concerts, String image) {
    this.name = name;
    this.bibliography = bibliography;
    this.concerts = concerts;
    this.image = image;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer artistid) {
    this.id = artistid;
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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public GenreEnum getGenre() {
    return genre;
  }

  public void setGenre(GenreEnum genre) {
    this.genre = genre;
  }

}
