package com.minesweeper.api.controller;

import com.minesweeper.api.controller.request.BoardRequest;
import com.minesweeper.api.exception.rest.RestResponse;
import com.minesweeper.api.model.entity.Board;
import com.minesweeper.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "Get board by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the board", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Board.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Board not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class)) })})
  public Board findBoard(@PathVariable String boardId) {

    return boardService.findOne(boardId);
  }

  @PostMapping
  @Operation(summary = "Create new board")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Board created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Board.class)) })})
  public Board createNewBoard(@RequestBody @Valid BoardRequest boardRequest) {

    return boardService.createNewBoard(boardRequest);
  }

  @PutMapping("/{boardId}/reveal")
  @Operation(summary = "Reveal a board tile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Tile revealed, return entire board", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Board.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Board not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class)) })})
  public Board reveal(
      @PathVariable String boardId,
      @RequestParam(value = "tile_row") int tileRow,
      @RequestParam(value = "tile_col") int tileCol) {

    return boardService.reveal(boardId, tileRow, tileCol);
  }
}