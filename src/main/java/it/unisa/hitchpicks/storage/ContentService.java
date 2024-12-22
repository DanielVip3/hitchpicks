package it.unisa.hitchpicks.storage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service class to do CRUD operations using Content(s).
 * In our implementation, only the "create" operation is allowed.
 */
@Service
@Transactional
public class ContentService {
  private final ContentRepository contentRepository;

  public ContentService(ContentRepository contentRepository) {
    this.contentRepository = contentRepository;
  }

  /**
   * Adds a Content object to the database.
   *
   * @param content - the Content object to persist
   * @return the same Content object which was persisted
   * @throws IllegalArgumentException if the Content is null
   */
  public Content create(Content content) {
    if (content == null) throw new IllegalArgumentException("Content object is null.");

    return contentRepository.save(content);
  }
}
