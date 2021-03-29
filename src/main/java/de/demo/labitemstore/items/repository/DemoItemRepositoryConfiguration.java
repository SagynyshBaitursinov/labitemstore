package de.demo.labitemstore.items.repository;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = {"demo", "test"})
@Configuration
public class DemoItemRepositoryConfiguration {

  @Bean
  public ItemRepository inMemoryItemRepository() {
    return new InMemoryItemRepository(new ArrayList<>());
  }
}
