package com.amplio.amplio.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Statistics {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer statsID;

  @OneToMany
  private List<Artist> topArtists;

  @OneToMany
  private List<Song> topSongs;

  public Statistics() {
    this.topArtists = null;
    this.topSongs = null;
  }

  public void addTopArtist(Artist artist) {
    this.topArtists.add(artist);
  }

  public void addTopSong(Song song) {
    this.topSongs.add(song);

  }

  public void delTopArtist(Artist artist) {
    this.topArtists.remove(artist);
  }

  public void delTopSong(Song song) {
    this.topSongs.remove(song);

  }

  public Integer getStatsID() {
    return statsID;
  }

  public void setStatsID(Integer statsID) {
    this.statsID = statsID;
  }

  public List<Artist> getTopArtists() {
    return topArtists;
  }

  public void setTopArtists(List<Artist> topArtists) {
    this.topArtists = topArtists;
  }

  public List<Song> getTopSongs() {
    return topSongs;
  }

  public void setTopSongs(List<Song> topSongs) {
    this.topSongs = topSongs;
  }
}

