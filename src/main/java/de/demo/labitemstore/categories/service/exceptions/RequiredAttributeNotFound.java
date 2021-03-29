package de.demo.labitemstore.categories.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequiredAttributeNotFound extends RuntimeException {

  private String attributeName;
}
