package com.amplio.amplio.forms;

import com.amplio.amplio.models.Song;

import java.util.List;
import java.util.UUID;

public class AlbumForm {
  private UUID artistID;
  private String labelName;
  private String date;
  private String title;
  private List<Song> songs;

  public UUID getArtistID() {
    return artistID;
  }

  public void setArtistID(UUID artistID) {
    this.artistID = artistID;
  }

  public String getLabelName() {
    return labelName;
  }

  public void setLabelName(String labelName) {
    this.labelName = labelName;
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
