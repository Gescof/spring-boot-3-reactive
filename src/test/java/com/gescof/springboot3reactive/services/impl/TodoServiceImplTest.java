package com.gescof.springboot3reactive.services.impl;

import com.gescof.springboot3reactive.exceptions.TodoNotFoundException;
import com.gescof.springboot3reactive.models.dtos.CreateTodoRequest;
import com.gescof.springboot3reactive.models.dtos.TodoResponse;
import com.gescof.springboot3reactive.models.dtos.UpdateTodoRequest;
import com.gescof.springboot3reactive.models.entities.Todo;
import com.gescof.springboot3reactive.models.mappers.TodoMapperImpl;
import com.gescof.springboot3reactive.repositories.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @Spy
    private TodoMapperImpl todoMapper;

    @InjectMocks
    private TodoServiceImpl todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "with {0} request expect to create {1} and return response {2}")
    @MethodSource("createTodoData")
    void createTodoTest(CreateTodoRequest createTodoRequest, Todo createdTodo, TodoResponse expectedResponse) {
        // Arrange
        given(todoRepository.save(any(Todo.class))).willReturn(Mono.just(createdTodo));

        // Act
        Mono<TodoResponse> result = todoService.create(createTodoRequest);

        // Assert
        StepVerifier.create(result)
                .expectNext(expectedResponse)
                .expectComplete()
                .verify();
    }

    @ParameterizedTest(name = "expect to find {0} and return response {1}")
    @MethodSource("getAllTodosData")
    void getAllTodosTest(Todo todo, TodoResponse expectedResponse) {
        // Arrange
        given(todoRepository.findAll()).willReturn(Flux.just(todo));

        // Act
        Flux<TodoResponse> result = todoService.getAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(expectedResponse)
                .expectComplete()
                .verify();
    }

    @ParameterizedTest(name = "with {0} identifier value expect to find {1} and return response {2}")
    @MethodSource("getTodoByIdData")
    void getByIdTest(Integer id, Todo todo, TodoResponse expectedResponse, boolean notFound) {
        // Arrange
        if (notFound) {
            given(todoRepository.findById(id)).willReturn(Mono.empty());
        } else {
            given(todoRepository.findById(id)).willReturn(Mono.just(todo));
        }

        // Act
        Mono<TodoResponse> result = todoService.getById(id);

        // Assert
        if (notFound) {
            StepVerifier.create(result)
                    .expectError(TodoNotFoundException.class)
                    .verify();
        } else {
            StepVerifier.create(result)
                    .expectNext(expectedResponse)
                    .expectComplete()
                    .verify();
        }
    }

    @ParameterizedTest(name = "with {0} identifier value and {1} request expect to find {2}, "
            + "persist {3} updated if present and return response {4}")
    @MethodSource("updateTodoData")
    void updateByIdTest(Integer id, UpdateTodoRequest updateTodoRequest, Todo existingTodo, Todo todoToUpdate,
                        TodoResponse expectedResponse, boolean notFound) {
        // Arrange
        if (notFound) {
            given(todoRepository.findById(id)).willReturn(Mono.empty());
        } else {
            given(todoRepository.findById(id)).willReturn(Mono.just(existingTodo));
            given(todoRepository.save(todoToUpdate)).willReturn(Mono.just(todoToUpdate));
        }

        // Act
        Mono<TodoResponse> result = todoService.updateById(id, updateTodoRequest);

        // Assert
        if (notFound) {
            StepVerifier.create(result)
                    .expectError(TodoNotFoundException.class)
                    .verify();
        } else {
            StepVerifier.create(result)
                    .expectNext(expectedResponse)
                    .expectComplete()
                    .verify();
        }
    }

    @ParameterizedTest(name = "with {0} identifier value expect to find {1} and delete it if present")
    @MethodSource("deleteTodoData")
    void deleteByIdTest(Integer id, Todo existingTodo, boolean notFound) {
        // Arrange
        if (notFound) {
            given(todoRepository.findById(id)).willReturn(Mono.empty());
        } else {
            given(todoRepository.findById(id)).willReturn(Mono.just(existingTodo));
            given(todoRepository.delete(existingTodo)).willReturn(Mono.empty());
        }

        // Act
        Mono<Void> result = todoService.deleteById(id);

        // Assert
        if (notFound) {
            StepVerifier.create(result)
                    .expectError(TodoNotFoundException.class)
                    .verify();
        } else {
            StepVerifier.create(result)
                    .expectComplete()
                    .verify();
        }
    }

    static Stream<Arguments> createTodoData() {
        CreateTodoRequest request1 = new CreateTodoRequest("Todo 1", false);
        Todo createdTodo1 = Todo.builder().id(1).title("Todo 1").completed(false).build();
        TodoResponse response1 = TodoResponse.builder().id(1).title("Todo 1").completed(false).build();

        CreateTodoRequest request2 = new CreateTodoRequest("Todo 2", true);
        Todo createdTodo2 = Todo.builder().id(2).title("Todo 2").completed(true).build();
        TodoResponse response2 = TodoResponse.builder().id(2).title("Todo 2").completed(true).build();

        return Stream.of(
                Arguments.of(request1, createdTodo1, response1),
                Arguments.of(request2, createdTodo2, response2)
        );
    }

    static Stream<Arguments> getAllTodosData() {
        Todo todo1 = Todo.builder().id(1).title("Todo 1").completed(false).build();
        TodoResponse response1 = TodoResponse.builder().id(1).title("Todo 1").completed(false).build();

        Todo todo2 = Todo.builder().id(2).title("Todo 2").completed(true).build();
        TodoResponse response2 = TodoResponse.builder().id(2).title("Todo 2").completed(true).build();

        return Stream.of(
                Arguments.of(todo1, response1),
                Arguments.of(todo2, response2)
        );
    }

    static Stream<Arguments> getTodoByIdData() {
        Todo todo1 = Todo.builder().id(1).title("Todo 1").completed(false).build();
        TodoResponse response1 = TodoResponse.builder().id(1).title("Todo 1").completed(false).build();

        Todo todo2 = Todo.builder().id(2).title("Todo 2").completed(true).build();
        TodoResponse response2 = TodoResponse.builder().id(2).title("Todo 2").completed(true).build();

        return Stream.of(
                Arguments.of(1, todo1, response1, false),
                Arguments.of(2, todo2, response2, false),
                Arguments.of(3, null, null, true) // notFound case
        );
    }

    static Stream<Arguments> updateTodoData() {
        UpdateTodoRequest updateRequest1 = new UpdateTodoRequest("Updated Todo 1", true);
        Todo existingTodo1 = Todo.builder().id(1).title("Todo 1").completed(false).build();
        Todo todoToUpdate1 = Todo.builder().id(1).title("Updated Todo 1").completed(true).build();
        TodoResponse response1 = TodoResponse.builder().id(1).title("Updated Todo 1").completed(true).build();

        UpdateTodoRequest updateRequest2 = new UpdateTodoRequest("Updated Todo 2", false);
        Todo existingTodo2 = Todo.builder().id(2).title("Todo 2").completed(true).build();
        Todo todoToUpdate2 = Todo.builder().id(2).title("Updated Todo 2").completed(false).build();
        TodoResponse response2 = TodoResponse.builder().id(2).title("Updated Todo 2").completed(false).build();

        return Stream.of(
                Arguments.of(1, updateRequest1, existingTodo1, todoToUpdate1, response1, false),
                Arguments.of(2, updateRequest2, existingTodo2, todoToUpdate2, response2, false),
                Arguments.of(3, null, null, null, null, true) // notFound case
        );
    }

    static Stream<Arguments> deleteTodoData() {
        Todo existingTodo1 = Todo.builder().id(1).title("Todo 1").completed(false).build();
        Todo existingTodo2 = Todo.builder().id(2).title("Todo 2").completed(true).build();

        return Stream.of(
                Arguments.of(1, existingTodo1, false),
                Arguments.of(2, existingTodo2, false),
                Arguments.of(3, null, true) // notFound case
        );
    }
}
