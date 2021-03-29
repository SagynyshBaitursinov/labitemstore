package de.demo.labitemstore.categories.repository;

import de.demo.labitemstore.categories.model.ItemCategory;
import de.demo.labitemstore.categories.repository.exceptions.CategoryAlreadyExistsException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemoryItemCategoryRepository implements ItemCategoryRepository {

  private final List<ItemCategory> persistedItemCategories;

  @Override
  public void save(ItemCategory itemCategory) {
    if (persistedItemCategories.contains(itemCategory)) {
      throw new CategoryAlreadyExistsException(itemCategory.getId());
    }
    persistedItemCategories.add(itemCategory);
  }

  @Override
  public Optional<ItemCategory> fetchById(String categoryId) {
    return persistedItemCategories.stream()
        .filter(itemCategory -> categoryId.equals(itemCategory.getId()))
        .findFirst();
  }

  @Override
  public void deleteAll() {
    persistedItemCategories.clear();
  }
}
