package it.unisa.hitchpicks.logic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  /**
   * Displays the "Home" page.
   *
   * @return the name of the view template for the home page
   */
  @GetMapping("/")
  public String home() {
    return "index";
  }
}