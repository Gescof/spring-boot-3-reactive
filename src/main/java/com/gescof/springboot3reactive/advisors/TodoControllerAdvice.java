package com.gescof.springboot3reactive.advisors;

import com.gescof.springboot3reactive.exceptions.TodoNotFoundException;
import com.gescof.springboot3reactive.models.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TodoControllerAdvice {

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTodoNotFoundException(final TodoNotFoundException ex) {
        return ErrorResponse.builder().message(ex.getMessage()).build();
    }
}
