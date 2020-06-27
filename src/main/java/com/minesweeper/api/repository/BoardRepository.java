package com.minesweeper.api.repository;

import com.minesweeper.api.model.entity.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {

}
