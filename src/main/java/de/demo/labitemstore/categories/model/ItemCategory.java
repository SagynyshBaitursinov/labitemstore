package de.demo.labitemstore.categories.model;

import de.demo.labitemstore.categories.service.exceptions.RequiredAttributeNotFound;
import de.demo.labitemstore.items.model.Item;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class ItemCategory {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String id;
    private Set<ItemCategoryAttribute> itemCategoryRequiredAttributes;

    public Builder() {
      this.itemCategoryRequiredAttributes = new HashSet<>();
    }

    public Builder id(String name) {
      this.id = name;
      return this;
    }

    public void addRequiredAttribute(String name, ItemCategoryAttribute.Type type) {
      this.itemCategoryRequiredAttributes.add(new ItemCategoryAttribute(name, type));
    }

    public ItemCategory build() {
      return new ItemCategory(this.id, new ArrayList<>(this.itemCategoryRequiredAttributes));
    }
  }

  private String id;
  private List<ItemCategoryAttribute> itemCategoryRequiredAttributes;

  public void validateItem(Item item) {
    itemCategoryRequiredAttributes.forEach(requiredItemCategoryAttribute ->
        Optional.ofNullable(item.getItemAttributes().get(requiredItemCategoryAttribute.getName()))
            .ifPresentOrElse(
                requiredItemCategoryAttribute::validateValueMatchingType,
                () -> {
                  throw new RequiredAttributeNotFound(requiredItemCategoryAttribute.getName());
                }));
  }
}
