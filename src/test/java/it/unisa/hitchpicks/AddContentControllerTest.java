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
  public void handleAddContent_film_success() {
    Content content = new Content();
    content.setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fcinema.everyeye.it%2Farticoli%" +
            "2Fspeciale-top-5-cinepanettoni-28093.html&psig=AOvVaw1Uka7QcORG6Vm96qDV4D03&ust=17343866349" +
            "16000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMD8mubjqooDFQAAAAAdAAAAABAE");
    content.setTitle("Merry Christmas");
    content.setType(ContentType.MOVIE);
    content.setState(ContentState.RELEASED);
    content.setSynopsis("Fabio ha due mogli, Selvaggia e Serena, e cerca in tutti i modi di evitare che si incontrino." +
            " Enrico invece detesta il suo futuro genero e non vuole accettarlo in famiglia. " +
            "Per entrambi il Natale sarà un momento difficile, nel quale equivoci e malintesi rischieranno di rovinare tutto.");
    content.setDirector("Neri Parenti");
    content.setDuration(98);
    content.setYear(2001);
    content.setIdIMDB("tt1375666");

    assertSuccessHandleAddContent(content);
  }

  @Test
  public void handleAddContent_TV_success() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertSuccessHandleAddContent(content);
  }

  @Test
  public void handleAddContent_invalid_image() {
    Content content = new Content();
    content.setImage("httpesse://%$££$”$%&/”£$%”£$%£$%£$.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }


  @Test
  public void handleAddContent_invalid_title() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Badddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
            "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }



  @Test
  public void handleAddContent_invalid_duration1() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(-1);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }


  @Test
  public void handleAddContent_invalid_duration2() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914000);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }


  @Test
  public void handleAddContent_invalid_year1() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(1657);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }


  @Test
  public void handleAddContent_invalid_year2() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2100);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }

  @Test
  public void handleAddContent_invalid_IdIMDB() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("$£%”£$%£$%");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }


  @Test
  public void handleAddContent_invalid_seasonsNumber1() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(-1);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }

  @Test
  public void handleAddContent_invalid_seasonsNumber2() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(1001);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }


  @Test
  public void handleAddContent_invalid_episodesNumber1() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(-1);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }



  @Test
  public void handleAddContent_invalid_episodesNumber2() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(65);
    content.setTotalEpisodesNumber(62);

    assertErrorHandleAddContent(content);
  }


  @Test
  public void handleAddContent_invalid_episodesTotalNumber1() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(-1);

    assertErrorHandleAddContent(content);
  }

  @Test
  public void handleAddContent_invalid_episodesTotalNumber2() {
    Content content = new Content();
    content.setImage("https://cdn.bestmovie.it/wp-content/uploads/2020/05/breaking-bad-1.jpg");
    content.setTitle("Breaking Bad");
    content.setType(ContentType.TV);
    content.setSynopsis("Breaking Bad è una serie televisiva statunitense che segue la storia di Walter White," +
            " un insegnante di chimica di scuola superiore che, dopo essere diagnosticato con un cancro ai polmoni " +
            "in fase terminale, decide di entrare nel mondo della produzione e vendita di metanfetamine per garantire" +
            " un futuro finanziario alla sua famiglia.");
    content.setState(ContentState.RELEASED);
    content.setDirector("Vince Gilligan");
    content.setDuration(2914);
    content.setYear(2008);
    content.setStreamingPlatforms(new ContentStreamingPlatforms[]{ContentStreamingPlatforms.NETFLIX,ContentStreamingPlatforms.PRIMEVIDEO});
    content.setGenres(new ContentGenres[]{ContentGenres.CRIME, ContentGenres.DRAMA});
    content.setIdIMDB("tt0903747");
    content.setSeasonsNumber(5);
    content.setEpisodesNumber(62);
    content.setTotalEpisodesNumber(62453535);

    assertErrorHandleAddContent(content);
  }

}
