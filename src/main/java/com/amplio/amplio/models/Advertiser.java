package com.amplio.amplio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Advertiser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer advertiserId;

  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @OneToMany
  private Set<Advertisement> ads;

  public Advertiser(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public Integer getAdvertiserId() {
    return advertiserId;
  }

  public void setAdvertiserId(Integer advertiserId) {
    this.advertiserId = advertiserId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Advertisement> getAds() {
    return ads;
  }

  public void addAd(Advertisement ad) {
    if(ads == null) {
      ads = new TreeSet<>();
    }
    ads.add(ad);
  }

  public void deleteAd(Advertisement ad) {
    ads.remove(ad);
  }
}
