package com.gescof.springboot3reactive.services;

import com.gescof.springboot3reactive.models.dtos.CreateTodoRequest;
import com.gescof.springboot3reactive.models.dtos.TodoResponse;
import com.gescof.springboot3reactive.models.dtos.UpdateTodoRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoService {

    /**
     * Creates new object form request.
     *
     * @param createTodoRequest the object request to create
     * @return {@link Mono} wrapping {@link TodoResponse} that contains the data from created object
     */
    Mono<TodoResponse> create(CreateTodoRequest createTodoRequest);

    /**
     * Gets all stored objects if present.
     *
     * @return {@link Flux} wrapping {@link TodoResponse} objects if present
     */
    Flux<TodoResponse> getAll();

    /**
     * Gets a stored object by its identifier if present.
     *
     * @param id the identifier
     * @return {@link Mono} wrapping {@link TodoResponse} that contains the data from object if present
     */
    Mono<TodoResponse> getById(Integer id);

    /**
     * Updates an existing stored object if present by its identifier.
     *
     * @param id the identifier
     * @param updateTodoRequest the object request to update
     * @return {@link Mono} wrapping {@link TodoResponse} that contains the data from updated object
     */
    Mono<TodoResponse> updateById(Integer id, UpdateTodoRequest updateTodoRequest);

    /**
     * Deletes an existing stored object if present by its identifier.
     *
     * @param id the identifier
     * @return {@link Mono} empty
     */
    Mono<Void> deleteById(Integer id);
}
