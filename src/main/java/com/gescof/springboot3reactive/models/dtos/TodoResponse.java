package com.gescof.springboot3reactive.models.dtos;

import lombok.Builder;

@Builder
public record TodoResponse(
        Integer id,
        String title,
        Boolean completed
) {
}
