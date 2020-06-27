package com.minesweeper.api.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class UserRequest {
  @NotBlank
  private String name;
  @Positive
  private int age;

  public UserRequest() {
  }

  public UserRequest(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}