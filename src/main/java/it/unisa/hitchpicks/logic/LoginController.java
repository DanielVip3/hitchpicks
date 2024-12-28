package it.unisa.hitchpicks.logic;

import it.unisa.hitchpicks.storage.User;
import it.unisa.hitchpicks.storage.UserService;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

  private final UserService userService;

  public LoginController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Displays the "Login" page.
   *
   * @return the name of the view template for the login page
   */
  @GetMapping("/login")
  public String login() {
    return "login";
  }

  /**
   * Executes login, by validating user credentials and establishing a session.
   *
   * @param username           the username provided by the user
   * @param password           the password provided by the user
   * @param redirectAttributes used to pass flash attributes, such as error messages
   * @param session            the current HTTP session to store user information upon successful login
   * @return a redirect URL to the "Add content" page on success, or to the same page with error messages on failure
   */
  @PostMapping("/login")
  public String handleLogin(
      @RequestParam String username,
      @RequestParam String password,
      RedirectAttributes redirectAttributes,
      HttpSession session
  ) {
    if (!isValidUsername(username) || !isValidPassword(password)) {
      redirectAttributes.addFlashAttribute("error", "Invalid username or password");

      return "redirect:/login";
    }

    try {
      String hashedPassword = hashPassword(password);

      User user = userService.find(username, hashedPassword);

      session.setAttribute("user", user);

      return "redirect:/admin/add-content";
    } catch (NoSuchElementException e) {
      redirectAttributes.addFlashAttribute("error", "User not found");

      return "redirect:/login";
    }  catch (IllegalArgumentException e) {
      redirectAttributes.addFlashAttribute("error", "Incorrect username or password");

      return "redirect:/login";
    }
  }

  /**
   * Checks if a username is valid (its length is between 3 and 25 characters).
   *
   * @param username the username
   * @return whether it's valid or not
   */
  private boolean isValidUsername(String username) {
    return username.length() >= 3 && username.length() <= 25;
  }

  /**
   * Checks if a password is valid (its length is between 8 and 255 characters).
   *
   * @param password the password
   * @return whether it's valid or not
   */
  private boolean isValidPassword(String password) {
    return password.length() >= 8 && password.length() <= 255 && password.matches("[A-Za-z0-9]+");
  }

  /**
   * Hashes a password using SHA-256 with UTF-8 encoding.
   *
   * @param password the password
   * @return the hashed password string
   * @throws RuntimeException if the SHA-256 algorithm can't be found in standard libraries
   */
  public static String hashPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error during password hashing", e);
    }
  }
}
