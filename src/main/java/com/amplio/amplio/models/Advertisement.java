package com.amplio.amplio.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Advertisement {

  // Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID adId;

  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = AdCategoryEnum.class)
  private List<AdCategoryEnum> categories;

  @Lob
  private byte[] image;

  private Double price;

  private Integer numClicks;

  // Constructors
  public Advertisement(ArrayList<AdCategoryEnum> categories, byte[] image, Double price, Integer numClicks) {
    this.categories = categories;
    this.image = image;
    this.price = price;
  }

  // Accessor and Mutator Methods
  public UUID getAdId() {
    return adId;
  }

  public void setAdId(UUID adId) {
    this.adId = adId;
  }

  public List<AdCategoryEnum> getCategories() {
    return categories;
  }

  public void setCategories(ArrayList<AdCategoryEnum> categories) {
    this.categories = categories;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
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
