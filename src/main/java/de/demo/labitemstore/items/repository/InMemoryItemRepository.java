package de.demo.labitemstore.items.repository;

import static java.util.stream.Collectors.toList;

import de.demo.labitemstore.items.model.Item;
import de.demo.labitemstore.items.repository.exceptioons.ItemAlreadyExistsException;
import java.util.List;

public class InMemoryItemRepository implements ItemRepository {

  private final List<Item> persistedItems;

  public InMemoryItemRepository(List<Item> persistedItems) {
    this.persistedItems = persistedItems;
  }

  @Override
  public void save(Item item) {
    if (persistedItems.contains(item)) {
      throw new ItemAlreadyExistsException(item.getId());
    }
    persistedItems.add(item);
  }

  @Override
  public List<Item> fetchByCategoryId(String categoryId) {
    return persistedItems.stream()
        .filter(item -> categoryId.equals(item.getCategoryId()))
        .collect(toList());
  }

  @Override
  public void deleteAll() {
    persistedItems.clear();
  }
}
