package com.amplio.amplio.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Advertisement {

  // Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer adId;

  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = AdCategoryEnum.class)
  private List<AdCategoryEnum> categories;

  private String image;

  private Double price;

  private Integer numClicks;

  // Constructors
  public Advertisement(ArrayList<AdCategoryEnum> categories, String image, Double price, Integer numClicks) {
    this.categories = categories;
    this.image = image;
    this.price = price;
  }

  // Accessor and Mutator Methods
  public Integer getAdId() {
    return adId;
  }

  public void setAdId(Integer adId) {
    this.adId = adId;
  }

  public List<AdCategoryEnum> getCategories() {
    return categories;
  }

  public void setCategories(ArrayList<AdCategoryEnum> categories) {
    this.categories = categories;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getNumClicks() {
    return numClicks;
  }

  public void setNumClicks(Integer numClicks) {
    this.numClicks = numClicks;
  }

  public void incrementClicks() {
    numClicks++;
  }
}
