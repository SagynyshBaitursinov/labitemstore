package de.demo.labitemstore.categories.repository.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryAlreadyExistsException extends RuntimeException {

  private final String id;
}
