package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = "Username")
)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID userId;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  private String email;

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  private Boolean isPremium;

  // TODO: Credit Card

  @Lob
  private byte[] profilePicture;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_followers",
      joinColumns = @JoinColumn(name = "following_id"),
      inverseJoinColumns = @JoinColumn(name = "follower_id")
  )
  private Set<User> followers;

  @ManyToMany(mappedBy = "followers")
  private Set<User> following;

  @OneToMany
  private Set<Song> savedSongs;

  @OneToMany
  private Set<Song> likedSongs;

  @OneToMany
  private Set<Song> dislikedSongs;

  @OneToMany
  private Set<Album> favoriteAlbums;

  @ElementCollection(targetClass = AdCategoryEnum.class)
  @Enumerated(EnumType.STRING)
  private List<AdCategoryEnum> adPrefs;

  @OneToOne
  private SongQueue songQueue;

  public User(String firstName, String lastName, String email, String username, String password, Boolean isPremium) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.username = username;
    this.password = password;
    this.isPremium = isPremium;
  }


  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public Set<User> getFollowers() {
    return followers;
  }

  public void setFollowers(Set<User> followers) {
    this.followers = followers;
  }

  public Set<User> getFollowing() {
    return following;
  }

  public void setFollowing(Set<User> following) {
    this.following = following;
  }

  public void follow(User follower) {
    this.followers.add(follower);
    follower.following.add(this);
  }

  public void unfollow(User follower) {
    this.followers.remove(follower);
    follower.following.remove(this);
  }

  public Set<Song> getSavedSongs() {
    return savedSongs;
  }

  public void saveSong(Song song) {
    if(savedSongs == null) {
      savedSongs = new TreeSet<>();
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
      likedSongs = new TreeSet<>();
    }
    likedSongs.add(song);
  }

  public Set<Song> getDislikedSongs() {
    return dislikedSongs;
  }

  public void dislikeSong(Song song) {
    if(dislikedSongs == null) {
      dislikedSongs = new TreeSet<>();
    }
    dislikedSongs.add(song);
  }

  public Set<Album> getFavoriteAlbums() {
    return favoriteAlbums;
  }

  public void favoriteAlbum(Album album) {
    if(favoriteAlbums == null) {
      favoriteAlbums = new TreeSet<>();
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

  //TODO: Methods like isFollower etc.

    /*
    * FUTURE BUILDS
    * BillingDetails
    * Login with FB?
     */
}
