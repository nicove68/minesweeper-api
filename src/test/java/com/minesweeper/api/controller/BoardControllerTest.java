package com.minesweeper.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeper.api.controller.request.BoardRequest;
import com.minesweeper.api.exception.RestControllerErrorHandler;
import com.minesweeper.api.model.entity.Board;
import com.minesweeper.api.service.BoardService;
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
public class BoardControllerTest {

  private static final String BASE_PATH = "/boards";

  private JacksonTester<BoardRequest> jsonBoardRequest;
  private MockMvc mvc;

  @InjectMocks
  private BoardController boardController;

  @Mock
  private BoardService boardService;

  @Before
  public void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(boardController)
        .setControllerAdvice(new RestControllerErrorHandler())
        .build();
  }

  @Test
  public void findBoard_ok() throws Exception {
    Board board = new Board();

    doReturn(board).when(boardService).findOne(anyString());

    mvc.perform(
        get(BASE_PATH + "/1234")
            .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void createNewBoard_ok() throws Exception {
    Board board = new Board();
    BoardRequest request = new BoardRequest(3, 3, 1, null);

    doReturn(board).when(boardService).createNewBoard(any(BoardRequest.class));

    mvc.perform(
        post(BASE_PATH)
            .contentType(APPLICATION_JSON)
            .content(this.jsonBoardRequest.write(request).getJson()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void reveal() throws Exception {
    Board board = new Board();

    doReturn(board).when(boardService).createNewBoard(any(BoardRequest.class));

    mvc.perform(
        put(BASE_PATH + "/1234/reveal?tile_row=0&tile_col=0")
            .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
