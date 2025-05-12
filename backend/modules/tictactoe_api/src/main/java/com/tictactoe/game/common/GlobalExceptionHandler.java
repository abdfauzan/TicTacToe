package com.tictactoe.game.common;

import com.tictactoe.game.dto.ProblemDetailDTO;
import com.tictactoe.game.exception.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetailDTO> apiClientException(ResponseStatusException ex) {

        ProblemDetailDTO problemDetail = new ProblemDetailDTO(ex.getHttpStatus().getReasonPhrase(), ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(problemDetail);
    }


}