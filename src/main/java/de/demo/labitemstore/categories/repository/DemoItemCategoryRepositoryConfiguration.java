package de.demo.labitemstore.categories.repository;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = {"demo", "test"})
@Configuration
public class DemoItemCategoryRepositoryConfiguration {

  @Bean
  public ItemCategoryRepository inMemoryItemCategoryRepository() {
    return new InMemoryItemCategoryRepository(new ArrayList<>());
  }
}
