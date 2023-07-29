package com.gescof.springboot3reactive.repositories;

import com.gescof.springboot3reactive.models.entities.Todo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TodoRepository extends ReactiveCrudRepository<Todo, Integer> {
}
