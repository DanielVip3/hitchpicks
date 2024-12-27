package it.unisa.hitchpicks.logic;

import it.unisa.hitchpicks.storage.*;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  @PostMapping("/admin/addcontent")
  public String handleAddContent(
      @Valid @ModelAttribute Content content,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    try {
      if (bindingResult.hasErrors()) {
        throw new IllegalArgumentException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
      }

      int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
      if (content.getYear() != null && content.getYear() > currentYear + 10) {
        throw new IllegalArgumentException("Year must be " + (currentYear + 10) + " or previous.");
      }

      if (content.getType() == ContentType.TV) {
        if (content.getEpisodesNumber() == null) {
          throw new IllegalArgumentException("Current episodes must not be empty.");
        } else if (content.getTotalEpisodesNumber() == null) {
          throw new IllegalArgumentException("Total episodes must not be empty.");
        } else if (content.getEpisodesNumber() > content.getTotalEpisodesNumber()) {
          throw new IllegalArgumentException("Current episodes must be less or equal to total episodes.");
        }
      }

      contentService.create(content);

      return "redirect:/admin/addcontent";
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

      return "redirect:/admin/addcontent";
    }
  }
}