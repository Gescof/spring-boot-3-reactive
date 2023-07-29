package com.gescof.springboot3reactive.models.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public record TodoRequest(
        String title,
        Boolean completed
) {
}
