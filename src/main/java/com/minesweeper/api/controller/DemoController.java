package com.minesweeper.api.controller;

import com.minesweeper.api.controller.request.BoardRequest;
import com.minesweeper.api.model.entity.Board;
import com.minesweeper.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DemoController {

  private final BoardService boardService;

  @Autowired
  public DemoController(BoardService boardService) {
    this.boardService = boardService;
  }


  @GetMapping("/demo")
  public String showDemoHome(Model model) {

    return "home";
  }

  @GetMapping("/demo/create")
  public RedirectView localRedirect() {
    BoardRequest demoBoardRequest = new BoardRequest(8, 8 , 4, "demo");
    Board demoBoard = boardService.createNewBoard(demoBoardRequest);

    RedirectView redirectView = new RedirectView();
    redirectView.setUrl("/minesweeper-api/demo/play?board_id=" + demoBoard.getId());
    return redirectView;
  }

  @GetMapping("/demo/play")
  public String playDemoBoard(@RequestParam(value = "board_id") String boardId, Model model) {

      return "play";
  }
}
