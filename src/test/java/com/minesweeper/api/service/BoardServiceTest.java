package com.minesweeper.api.service;

import static com.minesweeper.api.model.BoardStatus.LOST;
import static com.minesweeper.api.model.BoardStatus.PLAYING;
import static com.minesweeper.api.model.BoardStatus.WON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.minesweeper.api.controller.request.BoardRequest;
import com.minesweeper.api.exception.rest.NotFoundException;
import com.minesweeper.api.model.BoardTile;
import com.minesweeper.api.model.entity.Board;
import com.minesweeper.api.repository.BoardRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class BoardServiceTest {

  private BoardService boardService;

  @Resource
  private BoardRepository boardRepository;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void init() {
    boardService = new BoardService(boardRepository);
    boardRepository.deleteAll();
  }


  @Test
  public void findBoard_ok() {
    BoardRequest request = new BoardRequest(3, 8, 14, null);
    Board board = boardService.createNewBoard(request);

    String boardId = board.getId();
    Board boardFromDB = boardService.findOne(boardId);

    assertEquals(3, boardFromDB.getRows());
    assertEquals(8, boardFromDB.getCols());
    assertEquals(14, boardFromDB.getMines());
  }

  @Test
  public void findBoard_fail() {
    exceptionRule.expect(NotFoundException.class);
    exceptionRule.expectMessage("404 NOT_FOUND:Board id 99999 not exists");

    boardService.findOne("99999");
  }

  @Test
  public void createNewBoard_ok() {
    BoardRequest request = new BoardRequest(3, 8, 14, "user_id");
    Board board = boardService.createNewBoard(request);

    String boardId = board.getId();
    Optional<Board> boardFromDB = boardRepository.findById(boardId);

    assertTrue(boardFromDB.isPresent());
    assertEquals("user_id", boardFromDB.get().getUserId());
    assertEquals(3, boardFromDB.get().getRows());
    assertEquals(8, boardFromDB.get().getCols());
    assertEquals(14, boardFromDB.get().getMines());
  }

  @Test
  public void reveal_whenTileIsAMine_returnBoardFinishedAsLost_ok() {
    BoardTile boardTile = new BoardTile(1, 0, 0, false, true, 0);
    List<BoardTile> tiles = new ArrayList<>();
    tiles.add(boardTile);

    Board board = new Board(3, 3, 1, PLAYING, "user", LocalDateTime.now().toString(), tiles);
    boardRepository.save(board);

    String boardId = board.getId();

    Board revealedBoard = boardService.reveal(boardId, 0, 0);

    assertEquals(LOST, revealedBoard.getStatus());
  }

  @Test
  public void reveal_whenTilesAreRevealedSafe_returnBoardFinishedAsWon_ok() {
    BoardTile boardTile1 = new BoardTile(1, 0, 0, false, true, 0);
    BoardTile boardTile2 = new BoardTile(2, 0, 1, false, false, 1);
    BoardTile boardTile3 = new BoardTile(3, 0, 2, false, false, 1);
    List<BoardTile> tiles = new ArrayList<>();
    tiles.add(boardTile1);
    tiles.add(boardTile2);
    tiles.add(boardTile3);

    Board board = new Board(3, 3, 1, PLAYING, "user", LocalDateTime.now().toString(), tiles);
    boardRepository.save(board);

    String boardId = board.getId();

    boardService.reveal(boardId, 0, 1);
    boardService.reveal(boardId, 0, 2);

    Board revealedBoard = boardService.findOne(boardId);

    assertEquals(WON, revealedBoard.getStatus());
  }
}
