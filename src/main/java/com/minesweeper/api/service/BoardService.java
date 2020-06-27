package com.minesweeper.api.service;

import static com.minesweeper.api.model.BoardStatus.PLAYING;

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
        .orElseThrow(() -> new NotFoundException("Board id " + boardId + " not exists"));
  }

  public Board createNewBoard(BoardRequest request) {
    int rows = request.getRows();
    int cols = request.getCols();
    int mines = request.getMines();

    logger.info("Creating new board with {} rows, {} cols and {} mines", rows, cols, mines);

    List<BoardTile> tiles = initializeEmptyTiles(rows, cols);
    putTheMines(mines, rows, cols, tiles);

    Board board = new Board(rows, cols, mines, PLAYING, LocalDateTime.now().toString(), tiles);

    calculateNeighborMineCount(board);

    return boardRepository.save(board);
  }

  private void calculateNeighborMineCount(Board board) {
    int boardRows = board.getRows();
    int boardCols = board.getCols();

    // Loop all the board
    for (int rowCoord = 0; rowCoord < boardRows ; rowCoord++) {
      for (int colCoord = 0; colCoord < boardCols ; colCoord++) {

        int row = rowCoord;
        int col = colCoord;
        Optional<BoardTile> tile = board.getTiles().stream()
            .filter(t -> t.getRow() == row)
            .filter(t -> t.getCol() == col)
            .findFirst();

        if (!tile.isPresent()) continue;

        int neighborMineCount = 0;

        // Loop around the tile and count neighbor mines
        for (int offsetRow = -1; offsetRow <= 1 ; offsetRow++) {
          for (int offsetCol = -1; offsetCol <= 1 ; offsetCol++) {

            if (offsetRow == 0 && offsetCol == 0) continue;

            int neighborRow = offsetRow + rowCoord;
            int neighborCol = offsetCol + colCoord;

            Optional<BoardTile> neighborTile = board.getTiles().stream()
                .filter(t -> t.getRow() == neighborRow)
                .filter(t -> t.getCol() == neighborCol)
                .findFirst();

            if (!neighborTile.isPresent()) continue;
            if (neighborTile.get().isMine())
              neighborMineCount++;
          }
        }

        tile.get().setNeighborMineCount(neighborMineCount);
      }
    }
  }

  private List<BoardTile> initializeEmptyTiles(int rows, int cols) {
    List<BoardTile> tiles = new ArrayList<>();
    int tileId = 1;
    for (int rowCoord = 0; rowCoord < rows ; rowCoord++) {
      for (int colCoord = 0; colCoord < cols ; colCoord++) {
        BoardTile tile = new BoardTile(tileId, rowCoord, colCoord, false, false, 0);
        tiles.add(tile);
        tileId++;
      }
    }
    return tiles;
  }

  private void putTheMines(int mines, int rows, int cols, List<BoardTile> tiles){
    int i=0;
    while (i < mines) {
      int rowCoord = randomNumberLessThan(rows);
      int colCoord = randomNumberLessThan(cols);

      Optional<BoardTile> tile = tiles.stream()
          .filter(t -> t.getRow() == rowCoord)
          .filter(t -> t.getCol() == colCoord)
          .findFirst();

      if (!tile.isPresent()) continue;
      if (tile.get().isMine()) continue;
      tile.get().setMine(true);
      i++;
    }
  }

  private int randomNumberLessThan(int max) {
    return random.nextInt(max);
  }
}
