package it.unisa.hitchpicks.storage;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
  List<User> findByNameAndPassword(String name, String password);
}
