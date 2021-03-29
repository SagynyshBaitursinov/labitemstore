package de.demo.labitemstore.items.model;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@Getter
public class Item {

  public static Item.Builder builder() {
    return new Item.Builder();
  }

  public static class Builder {

    private String id;
    private String categoryId;
    private Map<String, String> itemAttributes;

    public Builder() {
      this.itemAttributes = new HashMap<>();
    }

    public Item.Builder id(String name) {
      this.id = name;
      return this;
    }

    public Item.Builder categoryId(String categoryId) {
      this.categoryId = categoryId;
      return this;
    }

    public void addAttribute(String name, String value) {
      this.itemAttributes.put(name, value);
    }

    public Item build() {
      return new Item(this.id, this.categoryId, this.itemAttributes);
    }
  }

  private String id;
  private String categoryId;
  private Map<String, String> itemAttributes;
}
