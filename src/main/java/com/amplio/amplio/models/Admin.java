package com.amplio.amplio.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


public class Admin {
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
  private String username;

  @NotNull
  private String password;
}
