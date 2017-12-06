package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer albumId;

  @NotNull
  private String title;

  private String image;

  @NotNull
  @OneToOne
  private Artist artist;
  private Date date;

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

  public Integer getAlbumId() {
    return albumId;
  }

  public void setAlbumId(Integer albumId) {
    this.albumId = albumId;
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
}
