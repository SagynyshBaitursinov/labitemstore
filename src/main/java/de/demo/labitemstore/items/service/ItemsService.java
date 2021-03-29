package de.demo.labitemstore.items.service;

import static java.util.stream.Collectors.toList;

import de.demo.labitemstore.categories.service.ItemCategoriesService;
import de.demo.labitemstore.items.dto.CategoryItemsDto;
import de.demo.labitemstore.items.dto.ItemDto;
import de.demo.labitemstore.items.dto.ItemDto.ItemAttributeDto;
import de.demo.labitemstore.items.model.Item;
import de.demo.labitemstore.items.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemsService {

  private final ItemCategoriesService itemCategoriesService;
  private final ItemRepository itemRepository;

  public void save(ItemDto itemDto, String categoryId) {
    var item = createItem(itemDto, categoryId);
    itemCategoriesService.validate(item, categoryId);
    itemRepository.save(item);
  }

  private Item createItem(ItemDto itemDto, String categoryId) {
    var itemBuilder = Item.builder()
        .id(itemDto.getId())
        .categoryId(categoryId);
    itemDto.getAttributes().forEach(itemAttribute ->
        itemBuilder.addAttribute(itemAttribute.getName(), itemAttribute.getValue()));
    return itemBuilder.build();
  }

  public CategoryItemsDto listItems(String categoryId) {
    var items = itemRepository.fetchByCategoryId(categoryId).stream()
        .map(this::toItemDto)
        .collect(toList());
    return new CategoryItemsDto(items);
  }

  private ItemDto toItemDto(Item item) {
    var attributes = item.getItemAttributes().entrySet().stream()
        .map(entry -> new ItemAttributeDto(entry.getKey(), entry.getValue()))
        .collect(toList());
    return new ItemDto(item.getId(), attributes);
  }
}
