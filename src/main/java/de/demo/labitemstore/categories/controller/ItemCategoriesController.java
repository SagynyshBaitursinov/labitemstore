package de.demo.labitemstore.categories.controller;

import de.demo.labitemstore.categories.dto.CategoryInformationDto;
import de.demo.labitemstore.categories.service.ItemCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemCategoriesController {

  public static final String OK = "OK";

  private final ItemCategoriesService itemCategoriesService;

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/create-new-category",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createNewCategory(@RequestBody CategoryInformationDto categoryInformation) {
    itemCategoriesService.storeNewCategory(categoryInformation);
    return ResponseEntity.ok(OK);
  }
}
