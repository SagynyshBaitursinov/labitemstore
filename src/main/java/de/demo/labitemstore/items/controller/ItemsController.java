package de.demo.labitemstore.items.controller;

import de.demo.labitemstore.items.dto.CategoryItemsDto;
import de.demo.labitemstore.items.dto.ItemDto;
import de.demo.labitemstore.items.service.ItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemsController {

  private static final String OK = "OK";

  private final ItemsService itemsService;

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/{category-id}/create-item",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createNewItem(@PathVariable("category-id") String categoryId,
                                              @RequestBody ItemDto itemDto) {
    itemsService.save(itemDto, categoryId);
    return ResponseEntity.ok(OK);
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/{category-id}/list-items",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryItemsDto> listItems(@PathVariable("category-id") String categoryId) {
    return ResponseEntity.ok(itemsService.listItems(categoryId));
  }
}
