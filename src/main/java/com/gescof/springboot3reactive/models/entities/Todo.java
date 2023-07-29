package com.gescof.springboot3reactive.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("todos")
public class Todo {
    @Id
    @Column("id")
    private Integer id;

    @Column("title")
    private String title;

    @Column("completed")
    private Boolean completed;
}
