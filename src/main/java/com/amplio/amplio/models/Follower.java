package com.amplio.amplio.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Follower {
  @Id
  @NotNull
  private Integer userId;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private String userName;
  private byte[] profilePicture;

  public Follower(Integer userId, String firstName, String lastName, String userName, byte[] profilePicture) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.profilePicture = profilePicture;
  }

  public Follower(User user) {
    this.userId = user.getUserId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.userName = user.getLastName();
    this.profilePicture = user.getProfilePicture();
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public byte[] getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(byte[] profilePicture) {
    this.profilePicture = profilePicture;
  }
}
