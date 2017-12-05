package com.amplio.amplio.forms;

import com.amplio.amplio.models.Song;

import java.util.List;
import java.util.Set;

public class AlbumForm {
  private int artistID;
  private String date;
  private String title;
  private List<Song> songs;
  private Set<String> genres;

  public Set<String> getGenres() {
    return genres;
  }

  public void setGenres(Set<String> genres) {
    this.genres = genres;
  }

  public int getArtistID() {
    return artistID;
  }

  public void setArtistID(int artistID) {
    this.artistID = artistID;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Song> getSongs() {
    return songs;
  }

  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }
}
