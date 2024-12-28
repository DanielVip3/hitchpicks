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

  @GetMapping("/login")
  public String login() {
    return "login";
  }

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

      return "redirect:/admin/addcontent";
    } catch (NoSuchElementException e) {
      redirectAttributes.addFlashAttribute("error", "User not found");

      return "redirect:/login";
    }  catch (IllegalArgumentException e) {
      redirectAttributes.addFlashAttribute("error", "Incorrect username or password");

      return "redirect:/login";
    }
  }

  private boolean isValidUsername(String username) {
    return username.length() >= 3 && username.length() <= 25;
  }

  private boolean isValidPassword(String password) {
    return password.length() >= 8 && password.length() <= 255 && password.matches("[A-Za-z0-9]+");
  }

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
