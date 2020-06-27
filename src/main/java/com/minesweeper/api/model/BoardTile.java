package com.minesweeper.api.model;

public class BoardTile {
  private int id;
  private int row;
  private int col;
  private boolean revealed;
  private BoardTileDisplay display;

  public BoardTile() {
  }

  public BoardTile(int id, int row, int col, boolean revealed, BoardTileDisplay display) {
    this.id = id;
    this.row = row;
    this.col = col;
    this.revealed = revealed;
    this.display = display;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public BoardTileDisplay getDisplay() {
    return display;
  }

  public void setDisplay(BoardTileDisplay display) {
    this.display = display;
  }
}