package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
public class SongQueue {

  @NotNull
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID qID;

  @NotNull
  @OneToOne
  private User user;

  @OneToMany
  private List<Song> songs;

  private Integer currentSongIndex;

  @NotNull
  private Boolean isRadio;

  @NotNull
  private Boolean repeatSongs;

  @NotNull
  private Boolean shuffleSongs;

  public SongQueue(User user) {
    this.user = user;
    this.isRadio = false;
    this.repeatSongs = false;
    this.shuffleSongs = false;
  }

  public void toggleRepeat(Boolean toggle) {
    repeatSongs = toggle;
  }

  public void toggleShuffle(Boolean toggle) {
    shuffleSongs = toggle;
  }

  public Song getNextSong() {
    return songs.get(currentSongIndex + 1);
  }

  public Song getPrevSong() {
    return songs.get(currentSongIndex - 1);
  }

  public UUID getqID() {
    return qID;
  }

  public void setqID(UUID qID) {
    this.qID = qID;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Song> getSongs() {
    return songs;
  }

  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }

  public Integer getCurrentSongIndex() {
    return currentSongIndex;
  }

  public void setCurrentSongIndex(Integer currentSongIndex) {
    this.currentSongIndex = currentSongIndex;
  }

  public Boolean getRadio() {
    return isRadio;
  }

  public void setRadio(Boolean radio) {
    isRadio = radio;
  }

  public Boolean getRepeatSongs() {
    return repeatSongs;
  }

  public void setRepeatSongs(Boolean repeatSongs) {
    this.repeatSongs = repeatSongs;
  }

  public Boolean getShuffleSongs() {
    return shuffleSongs;
  }

  public void setShuffleSongs(Boolean shuffleSongs) {
    this.shuffleSongs = shuffleSongs;
  }
}
