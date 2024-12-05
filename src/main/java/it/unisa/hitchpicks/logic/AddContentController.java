package it.unisa.hitchpicks.logic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddContentController {
  @GetMapping("/admin/addcontent")
  public String addContent() {
    return "addcontent";
  }
}
