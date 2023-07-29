package com.gescof.springboot3reactive.models.mappers;

import com.gescof.springboot3reactive.models.dtos.CreateTodoRequest;
import com.gescof.springboot3reactive.models.dtos.TodoResponse;
import com.gescof.springboot3reactive.models.dtos.UpdateTodoRequest;
import com.gescof.springboot3reactive.models.entities.Todo;
import org.mapstruct.Mapper;

@Mapper
public interface TodoMapper {
    Todo map(CreateTodoRequest createTodoRequest);

    Todo map(Integer id, UpdateTodoRequest updateTodoRequest);

    TodoResponse map(Todo todo);
}
