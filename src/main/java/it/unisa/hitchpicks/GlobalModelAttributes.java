package it.unisa.hitchpicks;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAttributes {
  @ModelAttribute
  public void addGlobalAttributes(HttpSession session, Model model) {
    Object user = session.getAttribute("user");

    model.addAttribute("user", user);
  }
}