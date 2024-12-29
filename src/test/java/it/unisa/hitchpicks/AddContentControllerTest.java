package it.unisa.hitchpicks;

import it.unisa.hitchpicks.logic.AddContentController;
import it.unisa.hitchpicks.storage.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddContentControllerTest {
  AddContentController addContentController;

  @Mock
  ContentService contentService;

  @Mock
  RedirectAttributes redirectAttributes;

  @Mock
  HttpSession httpSession;

  @Autowired
  private Validator validator;

  @BeforeEach
  public void setupTests() {
    MockitoAnnotations.openMocks(this);

    addContentController = new AddContentController(contentService);

    // setting up validator, needed to validate Spring model annotations
    if (validator == null) {
      validator = new LocalValidatorFactoryBean();
      ((LocalValidatorFactoryBean) validator).afterPropertiesSet();
    }

    // this user is the one we're logged in with (and it's an admin)
    User user = new User();
    user.setAdmin(true);
    user.setName("Test user");
    user.setTag("test");
    user.setPassword("Test1234");
    user.setEmail("test@test.com");

    Mockito.when(httpSession.getAttribute("user")).thenReturn(user);
  }

  /**
   * Asserts that the handleAddContent run outputs an error with a specific {@link Content} input.
   * It expects an error message and a redirect to the same page.
   *
   * @param content the {@link Content} to test with
   */
  public void assertErrorHandleAddContent(Content content) {
    BindingResult bindingResult = new BeanPropertyBindingResult(content, "content");
    validator.validate(content, bindingResult);

    String result = addContentController.handleAddContent(content, bindingResult, redirectAttributes, httpSession);

    // checks if error message is set
    verify(redirectAttributes).addFlashAttribute(Mockito.eq("errorMessage"), Mockito.anyString());

    // checks if it redirects to same page
    assertEquals(result, "redirect:/admin/add-content");
  }

  /**
   * Asserts that the handleAddContent run executes successfully with a specific {@link Content} input.
   * It expects a success message, insert in database of the content, and a redirect to the same page.
   *
   * @param content the {@link Content} to test with
   */
  public void assertSuccessHandleAddContent(Content content) {
    BindingResult bindingResult = new BeanPropertyBindingResult(content, "content");
    validator.validate(content, bindingResult);

    String result = addContentController.handleAddContent(content, bindingResult, redirectAttributes, httpSession);

    // checks if success message is set
    verify(redirectAttributes).addFlashAttribute(Mockito.eq("successMessage"), Mockito.anyString());

    // checks if content is added to db
    verify(contentService).create(content);

    // checks if it redirects to same page
    assertEquals(result, "redirect:/admin/add-content");
  }

  /* NOTE: this test is temporary and done to show an example of test. */
  @Test
  public void handleAddContent_wrongImage() {
    Content content = new Content();
    content.setTitle("abcd");
    content.setImage("abcd");
    content.setType(ContentType.MOVIE);
    content.setState(ContentState.RELEASED);

    assertErrorHandleAddContent(content);
  }
}
