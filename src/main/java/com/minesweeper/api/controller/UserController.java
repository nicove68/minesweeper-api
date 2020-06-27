package com.minesweeper.api.controller;

import com.minesweeper.api.controller.request.UserRequest;
import com.minesweeper.api.exception.rest.RestResponse;
import com.minesweeper.api.model.entity.User;
import com.minesweeper.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "Get user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the user", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class)) })})
  public User findUser(@PathVariable String userId) {

    return userService.findOne(userId);
  }

  @PostMapping
  @Operation(summary = "Create new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) })})
  public User createNewUser(@RequestBody @Valid UserRequest userRequest) {

    return userService.createNewUser(userRequest);
  }

  @GetMapping("/{userId}/boards")
  @Operation(summary = "Get user boards by user id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found user boards", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) })})
  public List<String> findUserBoards(@PathVariable String userId) {

    return userService.findUserBoards(userId);
  }
}