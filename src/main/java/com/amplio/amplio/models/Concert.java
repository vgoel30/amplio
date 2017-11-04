package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.HashSet;
import java.text.SimpleDateFormat;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "ConcertID")
)
public class Concert {

    public Concert(HashSet<Artist> artists, SimpleDateFormat date, String location) {
        this.artists = artists;
        this.date = date;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID concertID;

    @NotNull
    private HashSet<Artist> artists;

    @NotNull
    private SimpleDateFormat date;

    @NotNull
    private String location; //TODO: Change to an appropriate location datatype

    public UUID getConcertID() {
        return concertID;
    }

    public void setConcertID(UUID concertID) {
        this.concertID = concertID;
    }

    public HashSet<Artist> getArtists() {
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
