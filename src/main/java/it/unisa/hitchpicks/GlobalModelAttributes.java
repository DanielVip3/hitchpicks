package it.unisa.hitchpicks;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
  @ModelAttribute
  public void addGlobalAttributes(HttpSession session, Model model) {
    Object user = session.getAttribute("user");

    model.addAttribute("user", user);
  }
}