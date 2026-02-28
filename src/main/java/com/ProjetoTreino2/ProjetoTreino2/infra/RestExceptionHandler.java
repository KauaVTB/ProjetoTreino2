package com.ProjetoTreino2.ProjetoTreino2.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.ProjetoTreino2.ProjetoTreino2.Exceptions.*;
import com.ProjetoTreino2.ProjetoTreino2.dto.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(
                        400,
                        mensagem,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        String mensagem = ("Termo de Requisição inválido: " + ex.getConstraintViolations().iterator().next().getMessage());

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(
                        400,
                        mensagem,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String mensagem = ("Tipo de Requisição inválido: " + ex.getName());

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(
                        400,
                        mensagem,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        String mensagem = "Valor não legível ou incorreto";

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(
                        400,
                        mensagem,
                        request.getRequestURI()
                ));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AnoPublicacaoInvalidoException.class)
    public ResponseEntity<String> handleAnoPublicacaoInvalidoException(AnoPublicacaoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AutorNullException.class)
    public ResponseEntity<String> handleAutorNullException(AutorNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LivroNotFoundException.class)
    public ResponseEntity<String> handleLivroNotFoundException(LivroNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LivroNullException.class)
    public ResponseEntity<String> handleLivroNullException(LivroNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClienteNullException.class)
    public ResponseEntity<String> handleClienteNullException(ClienteNullException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AutorNotFoundException.class)
    public ResponseEntity<String> handleAutorNotFoundException(AutorNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handleClienteNotFoundException(ClienteNotFoundException ex) {
        System.out.println("Entrou no handler de ClienteNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AutorIllegalArgumentException.class)
    public ResponseEntity<String> handleAutorIllegalArgumentException(AutorIllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClienteIllegalArgumentException.class)
    public ResponseEntity<String> handleClienteIllegalArgumentException(ClienteIllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LivroIllegalArgumentException.class)
    public ResponseEntity<String> handleLivroIllegalArgumentException(LivroIllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TituloIllegalArgumentException.class)
    public ResponseEntity<String> handleTituloIllegalArgumentException(TituloIllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    
}
