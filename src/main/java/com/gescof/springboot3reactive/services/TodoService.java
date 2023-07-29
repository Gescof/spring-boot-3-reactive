package com.gescof.springboot3reactive.services;

import com.gescof.springboot3reactive.exceptions.TodoNotFoundException;
import com.gescof.springboot3reactive.models.dtos.CreateTodoRequest;
import com.gescof.springboot3reactive.models.dtos.TodoResponse;
import com.gescof.springboot3reactive.models.dtos.UpdateTodoRequest;
import com.gescof.springboot3reactive.models.entities.Todo;
import com.gescof.springboot3reactive.models.mappers.TodoMapper;
import com.gescof.springboot3reactive.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public Mono<TodoResponse> create(final CreateTodoRequest createTodoRequest) {
        final Todo todoToCreate = todoMapper.map(createTodoRequest);
        final Mono<Todo> todoMonoCreated = todoRepository.save(todoToCreate);
        return todoMonoCreated.map(todoMapper::map);
    }

    public Flux<TodoResponse> getAll() {
        final Flux<Todo> foundFluxTodo = todoRepository.findAll();
        return foundFluxTodo.map(todoMapper::map);
    }

    public Mono<TodoResponse> getById(final Integer id) {
        return todoRepository.findById(id)
                .switchIfEmpty(getMonoErrorNotFoundException(id))
                .map(todoMapper::map);
    }

    public Mono<TodoResponse> updateById(final Integer id, final UpdateTodoRequest updateTodoRequest) {
        final Mono<Todo> updatedMonoTodo = todoRepository.findById(id)
                .switchIfEmpty(getMonoErrorNotFoundException(id))
                .flatMap(existingTodo -> {
                    final Todo todoToUpdate = todoMapper.map(id, updateTodoRequest);
                    return todoRepository.save(todoToUpdate);
                });
        return updatedMonoTodo.map(todoMapper::map);
    }

    public Mono<Void> deleteById(final Integer id) {
        return todoRepository.findById(id)
                .switchIfEmpty(getMonoErrorNotFoundException(id))
                .flatMap(todoRepository::delete);
    }

    private static Mono<Todo> getMonoErrorNotFoundException(final Integer id) {
        return Mono.error(new TodoNotFoundException("Todo not found with ID: " + id));
    }
}
