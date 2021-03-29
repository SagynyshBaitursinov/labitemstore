package de.demo.labitemstore.categories.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInformationDto {

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CategoryAttributeDto {

    private String name;
    private String type;
  }

  private String categoryId;
  private List<CategoryAttributeDto> categoryAttributes;
}
