package de.demo.labitemstore.categories.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemCategoryNotFound extends RuntimeException {

  private String categoryId;
}
