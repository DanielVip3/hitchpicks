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

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
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
    user.setName("Gianfranco Lazlo");
    user.setTag("test");
    user.setPassword("iloveshrek3");
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
    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorLongPassword() {
    String result = loginController.handleLogin(user.getName(),
            "Lorem ipsum dolor sit amet5, consectetur adipiscing elit. Donec a odio ligula. Suspendisse tempor elit eu sem tincidunt ultricies. Aenean nunc mi, molestie sit amet dolor quis, lobortis hendrerit mi. Quisque quis luctus leo, sit amet aliquam risus. Suspendisse porttitor nisi eget risus porta dignissim" ,
            redirectAttributes, httpSession);

    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorShortUsername() {
    String result = loginController.handleLogin("Gia", user.getPassword(), redirectAttributes, httpSession);

    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorNumericalPassword() {
    String result = loginController.handleLogin(user.getName(), "09112001", redirectAttributes, httpSession);

    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorLongUsername() {
    String result = loginController.handleLogin("Antonio Grio Focas Flavio Angelo Ducas Comneno Porrogenito Gagliardi De Curtis di Bisanzio",
                    user.getPassword(), redirectAttributes, httpSession);

    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorAlphabeticalPassword() {
    String result = loginController.handleLogin(user.getName(), "abcdefghijklmnopqrstuvz", redirectAttributes, httpSession);

    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_successfulAuthentication() {
    String result = loginController.handleLogin(user.getName(), user.getPassword(), redirectAttributes, httpSession);

    verify(redirectAttributes, never()).addFlashAttribute(eq("error"), Mockito.anyString());

    // Verify that the "user" attribute was correctly retrieved from the session
    Mockito.verify(httpSession).getAttribute("user");

    assertEquals(result, "redirect:/admin/add-content");
  }

  @Test
  public void logout_error() {
    String result = loginController.logout(httpSession);

    assertEquals(result, "redirect:/login");
  }

  @Test
  public void logout_success() {
    Mockito.when(httpSession.getAttribute("user")).thenReturn(user);

    String result = loginController.logout(httpSession);

    verify(httpSession).invalidate();

    assertEquals(result, "redirect:/");
  }

}

