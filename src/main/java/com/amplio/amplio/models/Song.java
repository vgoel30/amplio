package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Song {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
//  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private Integer songId;

  @NotNull
  private String songName;

  @NotNull
  private Integer numberPlays;

  @NotNull
  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = GenreEnum.class)
  private Set<GenreEnum> GenreEnum;

  @NotNull
  @ManyToOne
  private Artist artist;

  @NotNull
  @OneToOne
  private Album album;

  @NotNull
  private Integer duration;
  @Column(columnDefinition = "LONGTEXT")
  private String lyrics;


  private String path;

  public Song() {
  }

  public Song(String songName, Integer numberPlays, Set<GenreEnum> GenreEnum, Artist artist, Album album,
              Integer duration, String song) {
    this.songName = songName;
    this.numberPlays = numberPlays;
    this.GenreEnum = GenreEnum;
    this.artist = artist;
    this.album = album;
    this.duration = duration;
  }

  public String getSongName() {
    return songName;
  }

  public void setSongName(String songName) {
    this.songName = songName;
  }

  public Integer getSongId() {
    return songId;
  }

  public Integer getNumberPlays() {
    return numberPlays;
  }

  public void setNumberPlays(Integer numberPlays) {
    this.numberPlays = numberPlays;
  }

  public Set<GenreEnum> getGenreEnum() {
    return GenreEnum;
  }

  public void setGenreEnum(Set<GenreEnum> GenreEnum) {
    this.GenreEnum = GenreEnum;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public String getLyrics() {
    return lyrics;
  }

  public void setLyrics(String lyrics) {
    this.lyrics = lyrics;
  }

  public void incrementNumPlays() {
    this.numberPlays++;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }


}