package de.demo.labitemstore.items.controller;

import de.demo.labitemstore.categories.service.exceptions.ItemCategoryNotFound;
import de.demo.labitemstore.categories.service.exceptions.RequiredAttributeNotFound;
import de.demo.labitemstore.categories.service.exceptions.RequiredAttributeTypeDidNotMatch;
import de.demo.labitemstore.items.repository.exceptioons.ItemAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ItemControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Void> handleMethodArgumentNotValidException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Void> handleIllegalArgumentException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(ItemCategoryNotFound.class)
  public ResponseEntity<Void> handleItemCategoryNotFound(ItemCategoryNotFound exception) {
    log.error("Could not find category: {}", exception.getCategoryId());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(ItemAlreadyExistsException.class)
  public ResponseEntity<Void> handleItemAlreadyExistsException(ItemAlreadyExistsException exception) {
    log.error("Item already exists: {}", exception.getItemId());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(RequiredAttributeNotFound.class)
  public ResponseEntity<Void> handleRequiredAttributeNotFoundException(RequiredAttributeNotFound exception) {
    log.error("Could not find required attribute {}", exception.getAttributeName());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(RequiredAttributeTypeDidNotMatch.class)
  public ResponseEntity<Void> handleRequiredAttributeTypeDidNotMatchException(RequiredAttributeTypeDidNotMatch exception) {
    log.error("Could not match attribute value {} to attribute type {}",
        exception.getValue(), exception.getType().name());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
}
