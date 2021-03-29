package de.demo.labitemstore.categories.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.demo.labitemstore.categories.model.ItemCategory;
import de.demo.labitemstore.categories.model.ItemCategoryAttribute;
import de.demo.labitemstore.categories.model.ItemCategoryAttribute.Type;
import de.demo.labitemstore.categories.repository.ItemCategoryRepository;
import de.demo.labitemstore.categories.service.exceptions.ItemCategoryNotFound;
import de.demo.labitemstore.categories.service.exceptions.RequiredAttributeNotFound;
import de.demo.labitemstore.categories.service.exceptions.RequiredAttributeTypeDidNotMatch;
import de.demo.labitemstore.items.model.Item;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemCategoriesServiceTest {

  @Mock
  private ItemCategoryRepository itemCategoryRepository;

  private ItemCategoriesService itemCategoriesService;

  @BeforeEach
  void setUp() {
    this.itemCategoriesService = new ItemCategoriesService(itemCategoryRepository);
  }

  @Test
  void shouldThrowExceptionIfCategoryIsNotFound() {
    var item = new Item("1", "not-existing", Map.of("size", "123.12"));
    assertThrows(ItemCategoryNotFound.class, () -> itemCategoriesService.validate(item, "not-existing"));
  }

  @Test
  void shouldThrowExceptionIfCategoryTypeNumberIsNotMatched() {
    var itemType1 = new ItemCategory("item-type-1", List.of(new ItemCategoryAttribute("size", Type.NUMBER)));
    when(itemCategoryRepository.fetchById("item-type-1"))
        .thenReturn(Optional.of(itemType1));

    var item = new Item("1", "item-type-1", Map.of("size", "red"));
    assertThrows(RequiredAttributeTypeDidNotMatch.class, () -> itemCategoriesService.validate(item, "item-type-1"));
  }

  @Test
  void shouldNotThrowExceptionIfCategoryTypeNumberIsMatched() {
    var itemType1 = new ItemCategory("item-type-1", List.of(new ItemCategoryAttribute("size", Type.NUMBER)));
    when(itemCategoryRepository.fetchById("item-type-1"))
        .thenReturn(Optional.of(itemType1));

    var item = new Item("1", "item-type-1", Map.of("size", "123.12"));
    assertDoesNotThrow(() -> itemCategoriesService.validate(item, "item-type-1"));
  }

  @Test
  void shouldAlwaysMatchTextAttributeType() {
    var itemType1 = new ItemCategory("item-type-1", List.of(new ItemCategoryAttribute("color", Type.TEXT)));
    when(itemCategoryRepository.fetchById("item-type-1"))
        .thenReturn(Optional.of(itemType1));

    var item = new Item("1", "item-type-1", Map.of("color", "12344asd!!"));
    assertDoesNotThrow(() -> itemCategoriesService.validate(item, "item-type-1"));
  }

  @Test
  void shouldThrowExceptionIfRequiredAttributeNotFound() {
    var itemType1 = new ItemCategory("item-type-1", List.of(new ItemCategoryAttribute("color", Type.TEXT)));
    when(itemCategoryRepository.fetchById("item-type-1"))
        .thenReturn(Optional.of(itemType1));

    var item = new Item("1", "item-type-1", Map.of("size", "100"));
    assertThrows(RequiredAttributeNotFound.class, () -> itemCategoriesService.validate(item, "item-type-1"));
  }
}