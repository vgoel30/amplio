package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Playlist{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer playlistId;

  @NotNull
  private String title;

  private String image;

  @NotNull
  @OneToOne
  private User owner;

  @NotNull
  private boolean isPublic;

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
