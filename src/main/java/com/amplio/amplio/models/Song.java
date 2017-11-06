package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID songId;
    @NotNull
    private String songName;
    @NotNull
    private Integer numberPlays;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = GenreEnum.class)
    private Set<GenreEnum> GenreEnum;

    @NotNull
    @OneToMany
    private List<Artist> artists;

    @NotNull
    @OneToOne
    private Album album;

    @NotNull
    private Integer duration;
    private String lyrics;

    public Song(String songName, Integer numberPlays, Set<GenreEnum> GenreEnum, List<Artist> artists, Album album, Integer duration) {
        this.songName = songName;
        this.numberPlays = numberPlays;
        this.GenreEnum = GenreEnum;
        this.artists = artists;
        this.album = album;
        this.duration = duration;
    }
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public UUID getSongId() {
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

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
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
}