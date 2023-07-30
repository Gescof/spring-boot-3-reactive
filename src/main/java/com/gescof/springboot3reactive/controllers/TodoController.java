package com.gescof.springboot3reactive.controllers;

import com.gescof.springboot3reactive.models.dtos.CreateTodoRequest;
import com.gescof.springboot3reactive.models.dtos.TodoResponse;
import com.gescof.springboot3reactive.models.dtos.UpdateTodoRequest;
import com.gescof.springboot3reactive.services.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TodoResponse> create(@RequestBody final CreateTodoRequest createTodoRequest) {
        log.info("POST /todos");
        return todoService.create(createTodoRequest);
    }

    @GetMapping
    public Flux<TodoResponse> getAll() {
        log.info("GET /todos");
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<TodoResponse> getById(@PathVariable final Integer id) {
        log.info("GET /todos/{}", id);
        return todoService.getById(id);
    }

    @PutMapping("/{id}")
    public Mono<TodoResponse> update(@PathVariable final Integer id, @RequestBody final UpdateTodoRequest updateTodoRequest) {
        log.info("PUT /todos/{}", id);
        return todoService.updateById(id, updateTodoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable final Integer id) {
        log.info("DELETE /todos/{}", id);
        return todoService.deleteById(id);
    }
}
