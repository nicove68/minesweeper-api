package com.minesweeper.api.controller;

import com.minesweeper.api.controller.request.UserRequest;
import com.minesweeper.api.model.entity.User;
import com.minesweeper.api.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}")
  public User findUser(@PathVariable String userId) {

    return userService.findOne(userId);
  }

  @PostMapping
  public User createNewUser(@RequestBody @Valid UserRequest userRequest) {

    return userService.createNewUser(userRequest);
  }

  @GetMapping("/{userId}/boards")
  public List<String> findUserBoards(@PathVariable String userId) {

    return userService.findUserBoards(userId);
  }
}