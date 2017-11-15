package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Statistics {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID statsID;

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

  public UUID getStatsID() {
    return statsID;
  }

  public void setStatsID(UUID statsID) {
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

