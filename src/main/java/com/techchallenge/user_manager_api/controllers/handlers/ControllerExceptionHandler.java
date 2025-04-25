package com.techchallenge.user_manager_api.controllers.handlers;

import com.techchallenge.user_manager_api.dto.ExcecaoDTO;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExcecaoDTO> handlerResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(new ExcecaoDTO(e.getMessage(), status.value()));
    }

}
