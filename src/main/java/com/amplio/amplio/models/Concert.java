package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Concert {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer concertID;

  @NotNull
  @ManyToMany(mappedBy = "concerts")
  private Set<Artist> artists;

  @NotNull
  private SimpleDateFormat date;

  @NotNull
  private String location; //TODO: Change to an appropriate location datatype

  public Concert(HashSet<Artist> artists, SimpleDateFormat date, String location) {
    this.artists = artists;
    this.date = date;
    this.location = location;
  }

  public Integer getConcertID() {
    return concertID;
  }

  public void setConcertID(Integer concertID) {
    this.concertID = concertID;
  }

  public Set<Artist> getArtists() {
    return artists;
  }

  public void setArtists(HashSet<Artist> artists) {
    this.artists = artists;
  }

  public SimpleDateFormat getDate() {
    return date;
  }

  public void setDate(SimpleDateFormat date) {
    this.date = date;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
