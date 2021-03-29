package de.demo.labitemstore.categories.model;

import de.demo.labitemstore.categories.service.exceptions.RequiredAttributeTypeDidNotMatch;
import java.math.BigDecimal;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "name")
public class ItemCategoryAttribute {

  @RequiredArgsConstructor
  public enum Type {
    TEXT(value -> true),
    NUMBER(Type::isNumber);

    private final Function<String, Boolean> doesMatch;

    private static boolean isNumber(String value) {
      try {
        new BigDecimal(value);
        return true;
      } catch (NumberFormatException numberFormatException) {
        return false;
      }
    }

    public boolean matches(String value) {
      return this.doesMatch.apply(value);
    }
  }

  private String name;
  private Type type;

  public void validateValueMatchingType(String value) {
    if (!this.type.matches(value)) {
      throw new RequiredAttributeTypeDidNotMatch(value, this.type);
    }
  }
}
