package com.amplio.amplio.forms;

import com.amplio.amplio.models.Label;

public class ArtistForm {
  private String name;
  private String bibliography;
  private Label label;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBibliography() {
    return bibliography;
  }

  public void setBibliography(String bibliography) {
    this.bibliography = bibliography;
  }

  public Label getLabel() {
    return label;
  }

  public void setLabel(Label label) {
    this.label = label;
  }
}
