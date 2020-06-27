package com.minesweeper.api.service;

import com.minesweeper.api.controller.request.UserRequest;
import com.minesweeper.api.exception.rest.NotFoundException;
import com.minesweeper.api.model.entity.User;
import com.minesweeper.api.repository.BoardRepository;
import com.minesweeper.api.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;

  @Autowired
  public UserService(UserRepository userRepository, BoardRepository boardRepository) {
    this.userRepository = userRepository;
    this.boardRepository = boardRepository;
  }

  public User findOne(String userId) {
    logger.info("Find user with id {}", userId);

    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User id " + userId + " not exists"));
  }

  public User createNewUser(UserRequest request) {
    logger.info("Creating new user with name {} ", request.getName());

    User user = new User(request.getName(), request.getAge(), LocalDateTime.now().toString());

    return userRepository.save(user);
  }

  public List<String> findUserBoards(String userId) {
    logger.info("Find boards for user id {}", userId);

    return boardRepository.findAllByUserId(userId).stream()
        .map(b -> "/minesweeper-api/boards/"+b.getId())
        .collect(Collectors.toList());
  }
}
