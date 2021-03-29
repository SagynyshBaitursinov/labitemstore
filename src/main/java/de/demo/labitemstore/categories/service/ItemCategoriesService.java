package de.demo.labitemstore.categories.service;

import de.demo.labitemstore.categories.dto.CategoryInformationDto;
import de.demo.labitemstore.categories.model.ItemCategory;
import de.demo.labitemstore.categories.model.ItemCategoryAttribute.Type;
import de.demo.labitemstore.categories.repository.ItemCategoryRepository;
import de.demo.labitemstore.categories.service.exceptions.ItemCategoryNotFound;
import de.demo.labitemstore.items.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCategoriesService {

  private final ItemCategoryRepository itemCategoriesRepository;

  public void storeNewCategory(CategoryInformationDto categoryInformation) {
    var categoryBuilder = ItemCategory.builder()
        .id(categoryInformation.getCategoryId());
    categoryInformation.getCategoryAttributes().forEach(
        attributeDto -> categoryBuilder.addRequiredAttribute(
            attributeDto.getName(),
            Type.valueOf(attributeDto.getType())));
    itemCategoriesRepository.save(categoryBuilder.build());
  }

  public void validate(Item item, String categoryId) {
    itemCategoriesRepository.fetchById(categoryId)
        .ifPresentOrElse(
            itemCategory -> itemCategory.validateItem(item),
            () -> {
              throw new ItemCategoryNotFound(categoryId);
            });
  }
}
