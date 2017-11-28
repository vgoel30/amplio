package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SongCollection {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer collectionId;

  @NotNull
  private String title;

  @NotNull
  @OneToMany
  private List<Song> songs;

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

  public Integer getId() {
    return collectionId;
  }

}
