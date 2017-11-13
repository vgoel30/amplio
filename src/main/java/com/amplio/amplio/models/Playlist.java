package com.amplio.amplio.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Playlist extends SongCollection {
  private String image;

  @NotNull
  private User owner;

  @NotNull
  private boolean isPublic;

  public void addSong(Song song) {
    getSongs().add(song);
  }

  public void removeSong(Song song) {
    getSongs().remove(song);
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @OneToOne
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
