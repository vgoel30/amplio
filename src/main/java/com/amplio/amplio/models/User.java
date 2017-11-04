package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "Username")
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID userId;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String username;
    // NOTE: Use the PasswordEncoder interface
    @NotNull
    private String password;
    @NotNull
    private Boolean isPremium;

    private Set<User> followers;
    private Set<User> following;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "followingID"),
            inverseJoinColumns = @JoinColumn(name = "followerID"))
    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public void follow(User follower) {
        // Add a follower
        this.followers.add(follower);
        // Add user that you're following to your following set
        follower.following.add(this);
    }

    public void unfollow(User follower) {
        // Remove the follower
        this.followers.remove(follower);
        // Remove the user that you're following from your following set
        follower.following.remove(this);
    }

    @ManyToMany(mappedBy = "followers")
    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    // private Set<Song> savedSongs;
    // private List<AdCategory> adPrefs;
    // private Set<Song> likedSongs;
    // private Set<Song> dislikedSongs;
    // private Set<Album> favoriteAlbums;

    /*
    * FUTURE BUILDS
    * BillingDetails
    * Login with FB?
    *
     */

}
