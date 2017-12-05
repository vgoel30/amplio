package com.amplio.amplio.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Album extends SongCollection {

  @NotNull
  @OneToOne
  private Artist artist;
  private Date date;

  public Album() {
  }

  public Album(Artist artist, Date date, String title) {
    this.artist = artist;
    this.date = date;
    setTitle(title);
  }

  public Album(Artist artist) {
    this.artist = artist;
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
