package com.minesweeper.api.repository;

import com.minesweeper.api.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
