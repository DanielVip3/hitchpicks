package it.unisa.hitchpicks.logic;

import it.unisa.hitchpicks.storage.*;
import jakarta.servlet.http.HttpSession;
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

  /**
   * Displays the "Add content" page for admins.
   * If the user is not authenticated, he gets redirected to "Login" page.
   * If the user is authenticated but has no admin permissions, he gets redirected to "Home" page.
   *
   * @param session the current HTTP session to check whether user is authenticated and has admin permissions
   * @return the name of the view template for the "Add content" page
   */
  @GetMapping("/admin/add-content")
  public String addContent(HttpSession session) {
    if (session.getAttribute("user") == null) {
      return "redirect:/login";
    }

    if (!((User) session.getAttribute("user")).getAdmin()) {
      return "redirect:/";
    }

    return "add-content";
  }

  /**
   * Adds new content by validating the provided {@link Content} object and saving it.
   * It's called by a form in the "Add content" page, by admins.
   * If the user is not authenticated, he gets redirected to "Login" page.
   * If the user is authenticated but has no admin permissions, he gets redirected to "Home" page.
   *
   * @param content            the content object to be added, populated from the form submission
   * @param bindingResult      contains validation results for the {@code content} object
   * @param redirectAttributes used to pass flash attributes, such as error messages
   * @param session            the current HTTP session to check whether user is authenticated and has admin permissions
   * @return a redirect URL to the same page on success, or with error messages on failure
   */
  @PostMapping("/admin/add-content")
  public String handleAddContent(
      @Valid @ModelAttribute Content content,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      HttpSession session
  ) {
    try {
      if (session.getAttribute("user") == null) {
        return "redirect:/login";
      }

      if (!((User) session.getAttribute("user")).getAdmin()) {
        return "redirect:/";
      }

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

      redirectAttributes.addFlashAttribute("successMessage", "Content inserted successfully.");

      return "redirect:/admin/add-content";
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

      return "redirect:/admin/add-content";
    }
  }
}