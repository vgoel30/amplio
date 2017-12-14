package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotNull
  private String title;

  private String image;

  @NotNull
  @OneToOne
  private Artist artist;
  private Date date;

  @NotNull
  @Enumerated(EnumType.STRING)
  private GenreEnum genre;

  public Album() {
  }

  public Album(Artist artist, Date date, String title, String image) {
    this.artist = artist;
    this.date = date;
    this.title = title;
    this.image = image;
  }

  public Album(Artist artist) {
    this.artist = artist;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public GenreEnum getGenre() {
    return genre;
  }

  public void setGenre(GenreEnum genre) {
    this.genre = genre;
  }
}
