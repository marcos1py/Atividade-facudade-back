package com.crudDeProdutos.produtos.exception;

import com.crudDeProdutos.produtos.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(Exception e) {
        ApiResponse response = new ApiResponse(500, 
            "Algo deu errado, por favor tente novamente mais tarde.", 
            "Erro inesperado no servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiResponse response = new ApiResponse(404, 
            "Produto não encontrado com o ID informado.",
            "Por favor, verifique o ID ou tente outro.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponse response = new ApiResponse(400, 
            "Por favor, preencha todos os campos obrigatórios.", 
            e.getMessage()); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
