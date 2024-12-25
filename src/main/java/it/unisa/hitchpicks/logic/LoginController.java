package it.unisa.hitchpicks.logic;

import org.springframework.ui.Model;
import it.unisa.hitchpicks.storage.User;
import it.unisa.hitchpicks.storage.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
          Model model,
          HttpSession session
  ) {

    if (username.length() < 3 || username.length() > 25) {
      model.addAttribute("error", "Username must be between 3 and 25 characters.");
      return "login";
    }

    if (password.length() < 8 || password.length() > 255) {
      model.addAttribute("error", "Password must be between 8 and 255 characters.");
      return "login";
    }

    if (!password.matches("[A-Za-z0-9]+")) {
      model.addAttribute("error", "Password must be alphanumeric.");
      return "login";
    }

    try {
      String hashedPassword = hashPassword(password);

      User user = userService.find(username, hashedPassword);

      session.setAttribute("user", user);

      return "redirect:/home";

    } catch (Exception e) {
      /*
      model.addAttribute("error", "Incorrect username or password");
      */
      return "login";
    }
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
