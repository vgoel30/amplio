package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private String email;
  @NotNull
  @Column(unique = true)
  private String userName;
  @NotNull
  private String password;
  @NotNull
  private Boolean isPremium;
  private String profilePicture;
  @ManyToMany
  private Set<Follower> followers;
  @ManyToMany
  private Set<Follower> following;
  @ManyToMany
  private Set<Song> savedSongs;
  @ManyToMany
  private Set<Song> likedSongs;
  @ManyToMany
  private Set<Song> dislikedSongs;
  @ManyToMany
  private List<Song> songHistory;
  @OneToOne
  private SongQueue songQueue;
  @ManyToMany
  private Set<Album> savedAlbums;
  @ElementCollection(targetClass = AdCategoryEnum.class)
  @Enumerated(EnumType.STRING)
  private List<AdCategoryEnum> adPrefs;
  @ManyToMany
  private Set<Playlist> followedPlaylists;
  @ManyToMany
  private Set<Artist> followedArtists;
  @Transient
  private Set<Playlist> playlists;
  @NotNull
  private Boolean isBanned;
  @NotNull
  private Boolean isReported;

  public User() {
    firstName = "";
    lastName = "";
    email = "";
    userName = "";
    password = "";
    isPremium = false;
    followedPlaylists = new HashSet<Playlist>();
    followedArtists = new HashSet<Artist>();
    following = new HashSet<>();
    savedSongs = new HashSet<>();
    savedAlbums = new HashSet<>();
    songHistory = new ArrayList<>();
    isBanned = false;
    isReported = false;
  }

  public User(String firstName, String lastName, String email, String username, String password, Boolean isPremium) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.userName = username;
    this.password = password;
    this.isPremium = isPremium;
    this.following = new HashSet<>();
    this.followers = new HashSet<>();
    this.followedPlaylists = new HashSet<Playlist>();
    this.followedArtists = new HashSet<Artist>();
    this.savedSongs = new HashSet<>();
    this.savedAlbums = new HashSet<>();
    this.songHistory = new ArrayList<>();
    this.isBanned = false;
    this.isReported = false;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String username) {
    this.userName = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getPremium() {
    return isPremium;
  }

  public void setPremium(Boolean premium) {
    isPremium = premium;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public Set<Follower> getFollowers() {
    return followers;
  }

  public Set<Follower> getFollowing() {
    return following;
  }

  public Set<Song> getSavedSongs() {
    return savedSongs;
  }

  public Set<Song> getLikedSongs() {
    return likedSongs;
  }

  public void setFollowers(Set<Follower> followers) {
    this.followers = followers;
  }

  public void setFollowing(Set<Follower> following) {
    this.following = following;
  }

  public void setSavedSongs(Set<Song> savedSongs) {
    this.savedSongs = savedSongs;
  }

  public Boolean getBanned() {
    return isBanned;
  }

  public void setBanned(Boolean banned) {
    isBanned = banned;
  }

  public Boolean getReported() {
    return isReported;
  }

  public void setReported(Boolean reported) {
    isReported = reported;
  }

  public void likeSong(Song song) {
    if(likedSongs == null) {
      likedSongs = new HashSet<>();
    }
    likedSongs.add(song);
  }

  public Set<Song> getDislikedSongs() {
    return dislikedSongs;
  }

  public void dislikeSong(Song song) {
    if(dislikedSongs == null) {
      dislikedSongs = new HashSet<>();
    }
    dislikedSongs.add(song);
  }

  public Set<Album> getSavedAlbums() {
    return savedAlbums;
  }

  public void unfavoriteAlbum(Album album) {
    savedAlbums.remove(album);
  }

  public List<AdCategoryEnum> getAdPrefs() {
    return adPrefs;
  }

  public void setAdPrefs(ArrayList<AdCategoryEnum> adPrefs) {
    this.adPrefs = adPrefs;
  }

  public SongQueue getSongQueue() {
    return songQueue;
  }

  public void setSongQueue(SongQueue songQueue) {
    this.songQueue = songQueue;
  }

  public List<Song> getSongHistory() {
    return songHistory;
  }

  public Set<Playlist> getFollowedPlaylists() {
    return followedPlaylists;
  }

  public void setFollowedPlaylists(Set<Playlist> followedPlaylists) {
    this.followedPlaylists = followedPlaylists;
  }

  public Set<Artist> getFollowedArtists() {
    return followedArtists;
  }

  public void setFollowedArtists(Set<Artist> followedArtists) {
    this.followedArtists = followedArtists;
  }

  public Set<Playlist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(Set<Playlist> playlists) {
    this.playlists = playlists;
  }
}
