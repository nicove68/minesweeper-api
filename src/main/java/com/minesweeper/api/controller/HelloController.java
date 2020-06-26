package com.minesweeper.api.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

  @GetMapping
  public Map<String, String> hello() {
    logger.info("Say hello!");
    Map<String, String> map = new HashMap<>();
    map.put("hello", "world");
    return map;
  }
}