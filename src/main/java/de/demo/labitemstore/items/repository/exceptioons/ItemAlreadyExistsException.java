package de.demo.labitemstore.items.repository.exceptioons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ItemAlreadyExistsException extends RuntimeException {

  private final String itemId;
}
