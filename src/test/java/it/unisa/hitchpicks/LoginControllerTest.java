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

    // checks if error message is set
    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorShortUsername() {
    String result = loginController.handleLogin("Gia", user.getPassword(), redirectAttributes, httpSession);

    // checks if error message is set
    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorNumericalPassword() {
    String result = loginController.handleLogin(user.getName(), "09112001", redirectAttributes, httpSession);

    // checks if error message is set
    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorLongUsername() {
    String result = loginController.handleLogin("Antonio Grio Focas Flavio Angelo Ducas Comneno Porrogenito Gagliardi De Curtis di Bisanzio",
                    user.getPassword(), redirectAttributes, httpSession);

    // checks if error message is set
    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_errorAlphabeticalPassword() {
    String result = loginController.handleLogin(user.getName(), "abcdefghijklmnopqrstuvz", redirectAttributes, httpSession);

    // checks if error message is set
    verify(redirectAttributes).addFlashAttribute(eq("error"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/login");
  }

  @Test
  public void handleLogin_successfulAuthentication() {
    // Call the login method with the user's credentials
    String result = loginController.handleLogin(user.getName(), user.getPassword(), redirectAttributes, httpSession);

    // Verify that no error message was added to the redirect attributes
    verify(redirectAttributes, never()).addFlashAttribute(eq("error"), Mockito.anyString());

    // Verify that the "user" attribute was correctly retrieved from the session
    Mockito.verify(httpSession).getAttribute("user");

    // Verify that the redirection occurred to the desired page
    assertEquals(result, "redirect:/admin/add-content");
  }

  @Test
  public void testValidUsername_withValidUsername() throws Exception {
    // Access the private method using reflection
    Method isValidUsernameMethod = LoginController.class.getDeclaredMethod("isValidUsername", String.class);
    isValidUsernameMethod.setAccessible(true);

    assertTrue((boolean) isValidUsernameMethod.invoke(loginController, "validUser123"));
  }

  @Test
  public void testInvalidUsername_withTooShortUsername() throws Exception {
    // Access the private method using reflection
    Method isValidUsernameMethod = LoginController.class.getDeclaredMethod("isValidUsername", String.class);
    isValidUsernameMethod.setAccessible(true);

    // Test with a username that's too short ("Us")
    assertFalse((boolean) isValidUsernameMethod.invoke(loginController, "Us"));
  }

  @Test
  public void testInvalidUsername_withTooLongUsername() throws Exception {
    // Access the private method using reflection
    Method isValidUsernameMethod = LoginController.class.getDeclaredMethod("isValidUsername", String.class);
    isValidUsernameMethod.setAccessible(true);

    // Test with a username that's too long ("ThisUsernameIsWayTooLongForValidation")
    assertFalse((boolean) isValidUsernameMethod.invoke(loginController, "ThisUsernameIsWayTooLongForValidation"));
  }

  @Test
  public void testInvalidUsername_withEmptyUsername() throws Exception {
    // Access the private method using reflection
    Method isValidUsernameMethod = LoginController.class.getDeclaredMethod("isValidUsername", String.class);
    isValidUsernameMethod.setAccessible(true);

    // Test with an empty username
    assertFalse((boolean) isValidUsernameMethod.invoke(loginController, ""));
  }

  @Test
  public void testIsValidPassword_validPasswordWithAlphanumericCharacters() throws Exception {
    // Access the private method using reflection
    Method isValidPasswordMethod = LoginController.class.getDeclaredMethod("isValidPassword", String.class);
    isValidPasswordMethod.setAccessible(true);

    // Test with a valid password containing alphanumeric characters
    assertTrue((boolean) isValidPasswordMethod.invoke(loginController, "Password123"));
  }

  @Test
  public void testIsValidPassword_tooShortPassword() throws Exception {
    // Access the private method using reflection
    Method isValidPasswordMethod = LoginController.class.getDeclaredMethod("isValidPassword", String.class);
    isValidPasswordMethod.setAccessible(true);

    // Test with a password that's too short ("short")
    assertFalse((boolean) isValidPasswordMethod.invoke(loginController, "short"));
  }

  @Test
  public void testIsValidPassword_tooLongPassword() throws Exception {
    // Access the private method using reflection
    Method isValidPasswordMethod = LoginController.class.getDeclaredMethod("isValidPassword", String.class);
    isValidPasswordMethod.setAccessible(true);

    // Test with a password that's too long ("a".repeat(257))
    assertFalse((boolean) isValidPasswordMethod.invoke(loginController, "a".repeat(257)));
  }

  @Test
  public void testIsValidPassword_AlphabeticalCharacters() throws Exception {
    // Access the private method using reflection
    Method isValidPasswordMethod = LoginController.class.getDeclaredMethod("isValidPassword", String.class);
    isValidPasswordMethod.setAccessible(true);

    // Test with a password containing alphabetical characters
    assertFalse((boolean) isValidPasswordMethod.invoke(loginController, "thispasswordsucks"));
  }

}
