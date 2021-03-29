package de.demo.labitemstore.items.repository;

import de.demo.labitemstore.items.model.Item;
import java.util.List;

public interface ItemRepository {

  void save(Item item);

  List<Item> fetchByCategoryId(String categoryId);

  void deleteAll();
}
