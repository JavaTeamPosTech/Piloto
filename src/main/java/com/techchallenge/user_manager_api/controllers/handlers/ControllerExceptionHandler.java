package com.techchallenge.user_manager_api.controllers.handlers;

import com.techchallenge.user_manager_api.dto.ExcecaoDTO;
import com.techchallenge.user_manager_api.exceptions.resolver.ConstraintViolationMessageResolver;
import com.techchallenge.user_manager_api.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.exceptions.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final ConstraintViolationMessageResolver messageResolver;

    public ControllerExceptionHandler(ConstraintViolationMessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExcecaoDTO> handlerResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(new ExcecaoDTO(e.getMessage(), status.value()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExcecaoDTO> handlerUnauthorizedException(UnauthorizedException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status.value()).body(new ExcecaoDTO(e.getMessage(), status.value()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExcecaoDTO> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String mensagemAmigavel = messageResolver.resolveMessage(e);
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status.value()).body(new ExcecaoDTO(mensagemAmigavel, status.value()));
    }

}
