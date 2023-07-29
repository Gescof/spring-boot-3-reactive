package com.gescof.springboot3reactive.models.dtos;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message
) {
}

