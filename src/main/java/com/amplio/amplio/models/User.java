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
  private Integer userId;
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
  @Lob
  private byte[] profilePicture;
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
  private Set<Album> favoriteAlbums;
  @ElementCollection(targetClass = AdCategoryEnum.class)
  @Enumerated(EnumType.STRING)
  private List<AdCategoryEnum> adPrefs;
  @ManyToMany
  private Set<Playlist> followedPlaylists;


  public User() {
    firstName = "";
    lastName = "";
    email = "";
    userName = "";
    password = "";
    isPremium = false;
    followedPlaylists = new HashSet<Playlist>();
    following = new HashSet<>();
    songHistory = new ArrayList<>();
  }

  public User(String firstName, String lastName, String email, String username, String password, Boolean isPremium) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.userName = username;
    this.password = password;
    this.isPremium = isPremium;
    this.following = new HashSet<>();
    this.followedPlaylists = new HashSet<Playlist>();
    this.songHistory = new ArrayList<>();
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
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

  public byte[] getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(byte[] profilePicture) {
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

  public void saveSong(Song song) {
    if(savedSongs == null) {
      savedSongs = new HashSet<>();
    }
    savedSongs.add(song);
  }

  public void unsaveSong(Song song) {
    savedSongs.remove(song);
  }

  public Set<Song> getLikedSongs() {
    return likedSongs;
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

  //TODO: Methods like isFollower etc.

  public Set<Album> getFavoriteAlbums() {
    return favoriteAlbums;
  }

  public void favoriteAlbum(Album album) {
    if(favoriteAlbums == null) {
      favoriteAlbums = new HashSet<>();
    }
    favoriteAlbums.add(album);
  }

  public void unfavoriteAlbum(Album album) {
    favoriteAlbums.remove(album);
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

  public void addHistory(Song song) {
    if(songHistory == null) {
      songHistory = new ArrayList<>();
    }
    songHistory.add(song);
  }

  public Set<Playlist> getFollowedPlaylists() {
    return followedPlaylists;
  }

  public void setFollowedPlaylists(Set<Playlist> followedPlaylists) {
    this.followedPlaylists = followedPlaylists;
  }
}
