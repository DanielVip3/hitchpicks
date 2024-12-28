package it.unisa.hitchpicks.storage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service class to do CRUD operations using {@link User}(s).
 * In our implementation, only the "find" operation is allowed.
 */
@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Finds and creates a {@link User} object by searching in the database using its username and password.
   *
   * @param userName     the username
   * @param userPassword the password
   * @return the {@link User} object
   * @throws IllegalArgumentException if the username or password are null
   * @throws java.util.NoSuchElementException if the {@link User} is not found
   */
  public User find(String userName, String userPassword) {
    if (userName == null) throw new IllegalArgumentException("userName String is null.");
    if (userPassword == null) throw new IllegalArgumentException("userPassword is null.");

    return userRepository.findByNameAndPassword(userName, userPassword).getFirst();
  }
}
