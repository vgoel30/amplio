package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Playlist{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer playlistId;

  @NotNull
  private String title;

  @ManyToMany
  private List<Song> songs;

  private String description;

  private String image;

  @NotNull
  @OneToOne
  private User owner;

  @NotNull
  private boolean isPublic;

  public Playlist(String title, String description, String image, User owner) {
    this.title = title;
    this.description = description;
    this.image = image;
    this.owner = owner;
    this.songs = new ArrayList<>();
  }

  public Integer getPlaylistId() {
    return playlistId;
  }

  public void setPlaylistId(Integer playlistId) {
    this.playlistId = playlistId;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public void setPublic(boolean aPublic) {
    isPublic = aPublic;
  }
}
