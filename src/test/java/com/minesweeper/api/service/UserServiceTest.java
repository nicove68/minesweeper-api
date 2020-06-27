package com.minesweeper.api.service;

import static com.minesweeper.api.model.BoardStatus.PLAYING;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.minesweeper.api.controller.request.UserRequest;
import com.minesweeper.api.exception.rest.NotFoundException;
import com.minesweeper.api.model.entity.Board;
import com.minesweeper.api.model.entity.User;
import com.minesweeper.api.repository.BoardRepository;
import com.minesweeper.api.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataMongoTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

  private UserService userService;

  @Resource
  private UserRepository userRepository;

  @Resource
  private BoardRepository boardRepository;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void init() {
    userService = new UserService(userRepository, boardRepository);
    userRepository.deleteAll();
    boardRepository.deleteAll();
  }


  @Test
  public void findUser_ok() {
    UserRequest request = new UserRequest("test", 35);
    User user = userService.createNewUser(request);

    String userId = user.getId();
    User userFromDB = userService.findOne(userId);

    assertEquals("test", userFromDB.getName());
    assertEquals(35, userFromDB.getAge());
  }

  @Test
  public void findUser_fail() {
    exceptionRule.expect(NotFoundException.class);
    exceptionRule.expectMessage("404 NOT_FOUND:User id 99999 not exists");

    userService.findOne("99999");
  }

  @Test
  public void createNewUser_ok() {
    UserRequest request = new UserRequest("test", 35);
    User user = userService.createNewUser(request);

    String userId = user.getId();
    Optional<User> userFromDB = userRepository.findById(userId);

    assertTrue(userFromDB.isPresent());
    assertEquals("test", userFromDB.get().getName());
    assertEquals(35, userFromDB.get().getAge());
  }

  @Test
  public void findUserBoards_ok() {
    UserRequest request = new UserRequest("test", 35);
    User user = userService.createNewUser(request);
    String userId = user.getId();

    Board board1 = new Board(3, 3, 1, PLAYING, userId, LocalDateTime.now().toString(), emptyList());
    Board board2 = new Board(3, 3, 1, PLAYING, userId, LocalDateTime.now().toString(), emptyList());
    boardRepository.save(board1);
    boardRepository.save(board2);

    List<String> userBoards = userService.findUserBoards(userId);

    assertFalse(userBoards.isEmpty());
    assertEquals(2, userBoards.size());
  }
}
