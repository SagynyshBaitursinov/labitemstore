package de.demo.labitemstore.items.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import de.demo.labitemstore.categories.service.ItemCategoriesService;
import de.demo.labitemstore.categories.service.exceptions.ItemCategoryNotFound;
import de.demo.labitemstore.items.dto.ItemDto;
import de.demo.labitemstore.items.dto.ItemDto.ItemAttributeDto;
import de.demo.labitemstore.items.model.Item;
import de.demo.labitemstore.items.repository.ItemRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemsServiceTest {

  @Mock
  private ItemCategoriesService itemCategoriesService;

  @Mock
  private ItemRepository itemRepository;

  private ItemsService itemsService;

  @BeforeEach
  void setUp() {
    itemsService = new ItemsService(itemCategoriesService, itemRepository);
  }

  @Test
  void shouldCreateItemIfValid() {
    var category = "category-A";
    doNothing().when(itemCategoriesService).validate(any(Item.class), eq(category));

    var itemDto = new ItemDto("1", List.of(new ItemAttributeDto("color", "red")));
    itemsService.save(itemDto, category);

    var itemCaptor = ArgumentCaptor.forClass(Item.class);
    verify(itemRepository).save(itemCaptor.capture());
    var savedItem = itemCaptor.getValue();
    assertThat(savedItem.getId()).isEqualTo("1");
    assertThat(savedItem.getCategoryId()).isEqualTo(category);
    assertThat(savedItem.getItemAttributes().get("color")).isEqualTo("red");
  }

  @Test
  void shouldNotCreateItemIfValidationFailed() {
    var category = "category-A";
    doThrow(new ItemCategoryNotFound(category)).when(itemCategoriesService)
        .validate(any(Item.class), eq(category));

    var itemDto = new ItemDto("1", List.of(new ItemAttributeDto("color", "red")));
    assertThrows(ItemCategoryNotFound.class, () -> itemsService.save(itemDto, category));

    verify(itemRepository, never()).save(any(Item.class));
  }
}