package com.techchallenge.user_manager_api.api.controllers.handlers;

import com.techchallenge.user_manager_api.application.exceptions.ResourceNotFoundException;
import com.techchallenge.user_manager_api.application.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.application.exceptions.resolver.ConstraintViolationMessageResolver;
import com.techchallenge.user_manager_api.domain.dto.response.ErroValidacaoDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ExcecaoDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final ConstraintViolationMessageResolver messageResolver;

    public ControllerExceptionHandler(ConstraintViolationMessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExcecaoDTO> handlerResourceNotFoundException(ResourceNotFoundException e) {
        ExcecaoDTO response = new ExcecaoDTO(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
    null
        );
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(response);
    }

    @ExceptionHandler({UnauthorizedException.class, AccessDeniedException.class})
    public ResponseEntity<ExcecaoDTO> handlerUnauthorizedException(UnauthorizedException e) {
        ExcecaoDTO response = new ExcecaoDTO(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                null
        );
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status.value()).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExcecaoDTO> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ExcecaoDTO response = new ExcecaoDTO(
                "Violação de integridade",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(new ErroValidacaoDTO(messageResolver.resolveField(e), messageResolver.resolveMessage(e)))
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExcecaoDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErroValidacaoDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ErroValidacaoDTO(err.getField(), err.getDefaultMessage()))
                .toList();

        ExcecaoDTO response = new ExcecaoDTO(
                "Erro de validação",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                errors
        );
        return ResponseEntity.badRequest().body(response);
    }
}
