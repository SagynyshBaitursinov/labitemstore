package de.demo.labitemstore.categories.controller;

import de.demo.labitemstore.categories.repository.exceptions.CategoryAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ItemCategoriesControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Void> handleMethodArgumentNotValidException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Void> handleIllegalArgumentException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(CategoryAlreadyExistsException.class)
  public ResponseEntity<Void> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException exception) {
    log.warn("Could not create a new category as it already exists: {}", exception.getId());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
}
