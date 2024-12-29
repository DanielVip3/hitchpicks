package it.unisa.hitchpicks;

import it.unisa.hitchpicks.logic.LoginController;
import it.unisa.hitchpicks.storage.User;
import it.unisa.hitchpicks.storage.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {
  LoginController loginController;

  @Mock
  UserService userService;

  @Mock
  RedirectAttributes redirectAttributes;

  @Mock
  HttpSession httpSession;

  private User user;

  @BeforeEach
  public void setupTests() {
    MockitoAnnotations.openMocks(this);

    loginController = new LoginController(userService);

    // this user is the one present in database, which we can login to
    user = new User();
    user.setAdmin(true);
    user.setName("Test user");
    user.setTag("test");
    user.setPassword("Test1234");
    user.setEmail("test@test.com");

    Mockito.when(userService.find(user.getName(), user.getPassword())).thenReturn(user);

    // we're not logged in
    Mockito.when(httpSession.getAttribute("user")).thenReturn(null);
  }

  /* NOTE: this test is temporary and done to show an example of test. */
  @Test
  public void handleLogin_errorShortPassword() {
    String result = loginController.handleLogin(user.getName(), "a", redirectAttributes, httpSession);

    // checks if error message is set
    verify(redirectAttributes).addFlashAttribute(Mockito.eq("error"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/login");
  }
}
