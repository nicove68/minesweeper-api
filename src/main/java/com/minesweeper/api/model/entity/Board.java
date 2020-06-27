package com.minesweeper.api.model.entity;

import com.minesweeper.api.model.BoardStatus;
import com.minesweeper.api.model.BoardTile;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "boards")
public class Board {
  @Id
  private String id;
  private int rows;
  private int cols;
  private int mines;
  private BoardStatus status;
  private String userId;
  @Field("created_at")
  private String createdAt;
  @Field("updated_at")
  private String updatedAt;
  @Field("finished_at")
  private String finishedAt;
  private List<BoardTile> tiles;

  public Board() {
  }

  @PersistenceConstructor
  public Board(int rows, int cols, int mines, BoardStatus status, String userId, String createdAt, List<BoardTile> tiles) {
    this.rows = rows;
    this.cols = cols;
    this.mines = mines;
    this.status = status;
    this.userId = userId;
    this.createdAt = createdAt;
    this.tiles = tiles;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getCols() {
    return cols;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public int getMines() {
    return mines;
  }

  public void setMines(int mines) {
    this.mines = mines;
  }

  public BoardStatus getStatus() {
    return status;
  }

  public void setStatus(BoardStatus status) {
    this.status = status;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(String finishedAt) {
    this.finishedAt = finishedAt;
  }

  public List<BoardTile> getTiles() {
    return tiles;
  }

  public void setTiles(List<BoardTile> tiles) {
    this.tiles = tiles;
  }
}