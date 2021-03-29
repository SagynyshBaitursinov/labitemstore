package de.demo.labitemstore;

import static org.assertj.core.api.Assertions.assertThat;

import de.demo.labitemstore.categories.dto.CategoryInformationDto;
import de.demo.labitemstore.categories.dto.CategoryInformationDto.CategoryAttributeDto;
import de.demo.labitemstore.categories.model.ItemCategoryAttribute;
import de.demo.labitemstore.categories.model.ItemCategoryAttribute.Type;
import de.demo.labitemstore.categories.repository.ItemCategoryRepository;
import de.demo.labitemstore.items.dto.CategoryItemsDto;
import de.demo.labitemstore.items.dto.ItemDto;
import de.demo.labitemstore.items.dto.ItemDto.ItemAttributeDto;
import de.demo.labitemstore.items.repository.ItemRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Tag("integration")
public class LabItemStoreIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ItemCategoryRepository itemCategoryRepository;

  @Autowired
  private ItemRepository itemRepository;

  @BeforeEach
  void setUp() {
    itemCategoryRepository.deleteAll();
    itemRepository.deleteAll();
  }

  @Test
  void shouldCreateNewCategory() {
    var response = createNewCategory(new CategoryInformationDto("category-1",
        List.of(
            new CategoryAttributeDto("size", "NUMBER"),
            new CategoryAttributeDto("color", "TEXT"))));
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isEqualTo("OK");

    var savedCategory = itemCategoryRepository.fetchById("category-1");
    assertThat(savedCategory).isPresent();
    var category = savedCategory.get();
    assertThat(category.getId()).isEqualTo("category-1");
    assertThat(category.getItemCategoryRequiredAttributes()).hasSize(2);
    assertThat(category.getItemCategoryRequiredAttributes()).containsExactly(
        new ItemCategoryAttribute("size", Type.NUMBER),
        new ItemCategoryAttribute("color", Type.TEXT));
  }

  @Test
  void shouldNotSaveTwoCategoriesWithSameId() {
    var response = createNewCategory(new CategoryInformationDto("category-2",
        List.of(
            new CategoryAttributeDto("size", "NUMBER"),
            new CategoryAttributeDto("color", "TEXT"))));
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isEqualTo("OK");

    var response2 = createNewCategory(new CategoryInformationDto("category-2",
        Collections.emptyList()));

    assertThat(response2.getStatusCode().is4xxClientError()).isTrue();
  }

  @Test
  void shouldNotSaveCategoryWithUnknownAttributeType() {
    var response = createNewCategory(new CategoryInformationDto("category-3",
        List.of(
            new CategoryAttributeDto("size", "WHAT"),
            new CategoryAttributeDto("color", "TEXT"))));
    assertThat(response.getStatusCode().is4xxClientError()).isTrue();
  }

  @Test
  void shouldSaveNewItemsUnderCategory() {
    var response = createNewCategory(new CategoryInformationDto("category-4",
        List.of(
            new CategoryAttributeDto("size", "NUMBER"),
            new CategoryAttributeDto("color", "TEXT"))));
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isEqualTo("OK");

    response = createNewItem(
        new ItemDto("1",
            List.of(
                new ItemAttributeDto("size", "11"),
                new ItemAttributeDto("color", "red"))),
        "category-4");
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isEqualTo("OK");

    response = createNewItem(
        new ItemDto("2",
            List.of(
                new ItemAttributeDto("size", "13"),
                new ItemAttributeDto("color", "green"))),
        "category-4");
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isEqualTo("OK");

    var getItemsResponse = getItemsUnderCategory("category-4");
    assertThat(getItemsResponse.getStatusCode().is2xxSuccessful()).isTrue();
    var items = getItemsResponse.getBody();
    assertThat(items.getItems()).hasSize(2);

    var item1 = filterByItemId(items, "1");
    assertThat(item1).isPresent();
    assertThat(item1.get().getAttributes()).contains(
        new ItemAttributeDto("size", "11"),
        new ItemAttributeDto("color", "red"));

    var item2 = filterByItemId(items, "2");
    assertThat(item2).isPresent();
    assertThat(item2.get().getAttributes()).contains(
        new ItemAttributeDto("size", "13"),
        new ItemAttributeDto("color", "green"));
  }

  private Optional<ItemDto> filterByItemId(CategoryItemsDto categoryItemsDto, String itemId) {
    return categoryItemsDto.getItems().stream()
        .filter(item -> itemId.equals(item.getId()))
        .findFirst();
  }

  @Test
  void shouldNotSaveNewItemUnderUnknownCategory() {
    var response = createNewItem(
        new ItemDto("1",
            List.of(
                new ItemAttributeDto("size", "11"),
                new ItemAttributeDto("color", "red"))),
        "category-5");
    assertThat(response.getStatusCode().is4xxClientError()).isTrue();
  }

  @Test
  void shouldNotSaveNewItemIfDoesNotFitCategoryAttributes() {
    var response = createNewCategory(new CategoryInformationDto("category-6",
        List.of(
            new CategoryAttributeDto("size", "NUMBER"),
            new CategoryAttributeDto("color", "TEXT"))));
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isEqualTo("OK");

    response = createNewItem(
        new ItemDto("1",
            List.of(
                new ItemAttributeDto("size", "11"))),
        "category-6");
    assertThat(response.getStatusCode().is4xxClientError()).isTrue();
  }

  @Test
  void shouldNotSaveNewItemIfDuplicated() {
    var response = createNewCategory(new CategoryInformationDto("category-7",
        List.of(
            new CategoryAttributeDto("size", "NUMBER"))));
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isEqualTo("OK");

    response = createNewItem(
        new ItemDto("1",
            List.of(
                new ItemAttributeDto("size", "11"))),
        "category-7");
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

    response = createNewItem(
        new ItemDto("1",
            List.of(
                new ItemAttributeDto("size", "11"))),
        "category-7");
    assertThat(response.getStatusCode().is4xxClientError()).isTrue();
  }

  private ResponseEntity<String> createNewCategory(CategoryInformationDto categoryInformationDto) {
    return restTemplate.postForEntity(
        "http://localhost:" + port + "/create-new-category",
        categoryInformationDto,
        String.class);
  }

  private ResponseEntity<String> createNewItem(ItemDto itemDto,
                                               String categoryId) {
    return restTemplate.postForEntity(
        "http://localhost:" + port + "/" + categoryId + "/create-item",
        itemDto,
        String.class);
  }

  private ResponseEntity<CategoryItemsDto> getItemsUnderCategory(String categoryId) {
    return restTemplate.getForEntity(
        "http://localhost:" + port + "/" + categoryId + "/list-items",
        CategoryItemsDto.class);
  }
}
