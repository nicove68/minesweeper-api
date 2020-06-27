package com.minesweeper.api.controller.request;

import javax.validation.constraints.Positive;

public class BoardRequest {
  @Positive
  private int rows;
  @Positive
  private int cols;
  @Positive
  private int mines;

  public BoardRequest() {
  }

  public BoardRequest(int rows, int cols, int mines) {
    this.rows = rows;
    this.cols = cols;
    this.mines = mines;
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
}