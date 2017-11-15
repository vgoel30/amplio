package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
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
  @Column(unique = true)
  private String username;
  @NotNull
  private String password;
  @NotNull
  private Boolean isPremium;
  @Lob
  private byte[] profilePicture;
  @ManyToMany(cascade = CascadeType.ALL)
  private Set<User> followers;
  @ManyToMany(mappedBy = "followers")
  private Set<User> following;
  @ManyToMany(cascade = CascadeType.ALL)
  private Set<User> friends;
  @OneToMany
  private Set<Song> savedSongs;
  @OneToMany
  private Set<Song> likedSongs;
  @OneToMany
  private Set<Song> dislikedSongs;
  @OneToMany
  private List<Song> songHistory;
  @OneToOne
  private SongQueue songQueue;
  @OneToMany
  private Set<Album> favoriteAlbums;
  @ElementCollection(targetClass = AdCategoryEnum.class)
  @Enumerated(EnumType.STRING)
  private List<AdCategoryEnum> adPrefs;

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

  @JoinTable(
      name = "user_followers",
      joinColumns = @JoinColumn(name = "following_id"),
      inverseJoinColumns = @JoinColumn(name = "follower_id")
  )
  public Set<User> getFollowers() {
    return followers;
  }

  public Set<User> getFollowing() {
    return following;
  }

  public void follow(User toFollow) {
    if(following == null) {
      following = new HashSet<>();
    }
    following.add(toFollow);
    if(toFollow.followers== null) {
      toFollow.followers = new HashSet<>();
    }
    toFollow.followers.add(this);
  }

  public void unfollow(User toUnfollow) {
    if(following != null) {
      following.remove(toUnfollow);
    }
    if(toUnfollow.followers != null) {
      toUnfollow.followers.remove(this);
    }
  }

  public Set<User> getFriends() {
    return friends;
  }

  public void friend(User friend) {
    if(friends == null) {
      friends = new HashSet<>();
    }
    friends.add(friend);
  }

  public void unfriend(User friend) {
    if(friends != null) {
      friends.remove(friend);
    }
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

  //TODO: Methods like isFollower etc.

    /*
    * FUTURE BUILDS
    * BillingDetails
    * Login with FB?
     */
}
