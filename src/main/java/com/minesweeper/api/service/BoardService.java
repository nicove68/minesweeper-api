package com.minesweeper.api.service;

import static com.minesweeper.api.model.BoardStatus.PLAYING;
import static com.minesweeper.api.model.BoardTileDisplay.EMPTY;
import static com.minesweeper.api.model.BoardTileDisplay.MINE;

import com.minesweeper.api.controller.request.BoardRequest;
import com.minesweeper.api.exception.rest.NotFoundException;
import com.minesweeper.api.model.BoardTile;
import com.minesweeper.api.model.entity.Board;
import com.minesweeper.api.repository.BoardRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
  private static final Random random = new Random();
  private final BoardRepository boardRepository;

  @Autowired
  public BoardService(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public Board findOne(String boardId) {
    logger.info("Find board with id {}", boardId);

    return boardRepository.findById(boardId)
        .orElseThrow(NotFoundException::new);
  }

  public Board createNewBoard(BoardRequest request) {
    logger.info("Creating new board with {} rows, {} cols and {} mines", request.getRows(), request.getCols(), request.getMines());

    int rows = request.getRows();
    int cols = request.getCols();
    int mines = request.getMines();

    List<BoardTile> tiles = initializeEmptyTiles(rows, cols);
    putTheMines(mines, rows, cols, tiles);

    Board board = new Board(
        rows, cols, mines, PLAYING, LocalDateTime.now().toString(), tiles
    );

    return boardRepository.save(board);
  }

  private List<BoardTile> initializeEmptyTiles(int rows, int cols) {
    List<BoardTile> tiles = new ArrayList<>();
    int tileId = 1;
    for (int y = 0; y < rows ; y++) {
      for (int x = 0; x < cols ; x++) {
        BoardTile tile = new BoardTile(tileId, y, x, false, EMPTY);
        tiles.add(tile);
        tileId++;
      }
    }
    return tiles;
  }

  private void putTheMines(int mines, int rows, int cols, List<BoardTile> tiles){
    int i=0;
    while (i < mines) {
      int rowPos = randomNumberLessThan(rows);
      int colPos = randomNumberLessThan(cols);

      Optional<BoardTile> tile = tiles.stream()
          .filter(t -> t.getRow() == rowPos)
          .filter(t -> t.getCol() == colPos)
          .findFirst();

      if (!tile.isPresent()) continue;
      if (tile.get().getDisplay() == (MINE)) continue;
      tile.get().setDisplay(MINE);
      i++;
    }
  }

  private int randomNumberLessThan(int max) {
    return random.nextInt(max);
  }
}
