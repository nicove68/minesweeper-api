package com.minesweeper.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardTile {
  @JsonProperty("tile_id")
  private int tileId;
  private int row;
  private int col;
  private boolean revealed;
  private boolean mine;
  @JsonProperty("neighbor_mine_count")
  private int neighborMineCount;

  public BoardTile() {
  }

  public BoardTile(int tileId, int row, int col, boolean revealed, boolean mine, int neighborMineCount) {
    this.tileId = tileId;
    this.row = row;
    this.col = col;
    this.revealed = revealed;
    this.mine = mine;
    this.neighborMineCount = neighborMineCount;
  }

  public int getTileId() {
    return tileId;
  }

  public void setTileId(int tileId) {
    this.tileId = tileId;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public boolean isRevealed() {
    return revealed;
  }

  public void setRevealed(boolean revealed) {
    this.revealed = revealed;
  }

  public boolean isMine() {
    return mine;
  }

  public void setMine(boolean mine) {
    this.mine = mine;
  }

  public int getNeighborMineCount() {
    return neighborMineCount;
  }

  public void setNeighborMineCount(int neighborMineCount) {
    this.neighborMineCount = neighborMineCount;
  }
}