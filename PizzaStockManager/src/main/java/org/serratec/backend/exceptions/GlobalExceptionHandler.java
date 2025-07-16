package org.serratec.backend.exceptions; 

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        
        ErroResposta erroResposta = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                LocalDateTime.now(),
                Collections.singletonList(ex.getMessage()) 
        );

        return new ResponseEntity<>(erroResposta, HttpStatus.NOT_FOUND);
    }
    
    

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErroResposta erroResposta = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Argumento ilegal no corpo de requisição",
                LocalDateTime.now(),
                Collections.singletonList(ex.getMessage())
        );

        return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> ValidationException(ValidationException ex) {
        ErroResposta erroResposta = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                LocalDateTime.now(),
                Collections.singletonList(ex.getMessage())
        );

        return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErroResposta erroResposta = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Entidade não encontrada",
                LocalDateTime.now(),
                Collections.singletonList(ex.getMessage())
        );

        return new ResponseEntity<>(erroResposta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        ErroResposta erroResposta = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Elemento não encontrado",
                LocalDateTime.now(),
                Collections.singletonList(ex.getMessage())
        );

        return new ResponseEntity<>(erroResposta, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicate(DuplicateResourceException ex) {
        ErroResposta erroResposta = new ErroResposta(
        		HttpStatus.CONFLICT.value(),
        		"Item duplicado",
        		LocalDateTime.now(), 
        		Collections.singletonList(ex.getMessage())
        		
        );
        return new ResponseEntity<>(erroResposta, HttpStatus.CONFLICT);
    }
    
}