package com.minesweeper.api.controller;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeper.api.controller.request.UserRequest;
import com.minesweeper.api.exception.RestControllerErrorHandler;
import com.minesweeper.api.model.entity.User;
import com.minesweeper.api.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
public class UserControllerTest {

  private static final String BASE_PATH = "/users";

  private JacksonTester<UserRequest> jsonUserRequest;
  private MockMvc mvc;

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @Before
  public void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(userController)
        .setControllerAdvice(new RestControllerErrorHandler())
        .build();
  }

  @Test
  public void findUser_ok() throws Exception {
    User user = new User();

    doReturn(user).when(userService).findOne(anyString());

    mvc.perform(
        get(BASE_PATH + "/1234")
            .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void createNewUser_ok() throws Exception {
    User user = new User();
    UserRequest request = new UserRequest("test", 35);

    doReturn(user).when(userService).createNewUser(any(UserRequest.class));

    mvc.perform(
        post(BASE_PATH)
            .contentType(APPLICATION_JSON)
            .content(this.jsonUserRequest.write(request).getJson()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void findUserBoards_ok() throws Exception {
    doReturn(emptyList()).when(userService).findUserBoards(anyString());

    mvc.perform(
        get(BASE_PATH + "/1234/boards")
            .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
