package util;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import it.unisa.hitchpicks.model.Todo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.Date;

@ApplicationScoped
public class Startup {
  /**
   * This method is executed at the start of your application
   */
  public void start(@Observes StartupEvent evt) {
    // in DEV mode we seed some data
    if (LaunchMode.current() == LaunchMode.DEVELOPMENT) {
      Todo a = new Todo();
      a.task = "First item";
      a.persist();

      Todo b = new Todo();
      b.task = "Second item";
      b.completed = new Date();
      b.persist();
    }
  }
}
