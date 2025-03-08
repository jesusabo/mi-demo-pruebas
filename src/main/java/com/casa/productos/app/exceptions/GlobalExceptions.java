package com.casa.productos.app.exceptions;

import com.casa.productos.app.responseDTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ResourceIsAlreadyException.class)
    public Mono<ResponseEntity<?>> ResourceIsAlreadyMethod(ResourceIsAlreadyException ex) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder().message(ex.getMessage()).build();
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<?>> ResourceNotFoundMethod(ResourceNotFoundException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder().message(ex.getMessage()).build();
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO));
    }
}
