package de.demo.labitemstore.categories.service.exceptions;

import de.demo.labitemstore.categories.model.ItemCategoryAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequiredAttributeTypeDidNotMatch extends RuntimeException {

  private String value;
  private ItemCategoryAttribute.Type type;
}
