package de.demo.labitemstore.categories.repository;

import de.demo.labitemstore.categories.model.ItemCategory;
import java.util.Optional;

public interface ItemCategoryRepository {

  void save(ItemCategory itemCategory);

  Optional<ItemCategory> fetchById(String categoryId);

  void deleteAll();
}
