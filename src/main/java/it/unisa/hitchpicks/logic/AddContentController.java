package it.unisa.hitchpicks.logic;

import it.unisa.hitchpicks.storage.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddContentController {
  private final ContentService contentService;

  public AddContentController(ContentService contentService) {
    this.contentService = contentService;
  }

  @GetMapping("/admin/addcontent")
  public String addContent() {
    return "addcontent";
  }
}
