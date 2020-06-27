package com.minesweeper.api.controller;

import com.minesweeper.api.controller.request.BoardRequest;
import com.minesweeper.api.model.entity.Board;
import com.minesweeper.api.service.BoardService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
public class BoardController {

  private final BoardService boardService;

  @Autowired
  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/{boardId}")
  public Board findBoard(@PathVariable String boardId) {

    return boardService.findOne(boardId);
  }

  @PostMapping
  public Board createNewBoard(@RequestBody @Valid BoardRequest boardRequest) {

    return boardService.createNewBoard(boardRequest);
  }

  @PutMapping("/{boardId}/reveal")
  public Board reveal(
      @PathVariable String boardId,
      @RequestParam(value = "tile_row") int tileRow,
      @RequestParam(value = "tile_col") int tileCol) {

    return boardService.reveal(boardId, tileRow, tileCol);
  }
}