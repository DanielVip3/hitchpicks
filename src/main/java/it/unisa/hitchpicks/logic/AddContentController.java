package it.unisa.hitchpicks.logic;

import it.unisa.hitchpicks.storage.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

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
          @RequestParam("image") String image,
          @RequestParam("title") String title,
          @RequestParam("type") String type,
          @RequestParam("state") String state,
          @RequestParam("synopsis") String synopsis,
          @RequestParam("director") String director,
          @RequestParam("duration") String duration,
          @RequestParam("year") String year,
          @RequestParam("idIMDB") String idIMDB,
          @RequestParam("seasonsNumber") String seasonsNumber,
          @RequestParam("episodesNumber") String episodesNumber,
          @RequestParam("totalEpisodesNumber") String totalEpisodesNumber,
          @RequestParam("genres") String genres,
          @RequestParam("streamingPlatforms") String streamingPlatforms
  ) {
    // Crea un oggetto Content
    Content content = new Content();

    content.setImage(image);
    content.setTitle(title);

    content.setType(ContentType.valueOf(type.toUpperCase()));  // Assicurati che i valori siano compatibili con l'enum
    content.setState(ContentState.valueOf(state.toUpperCase()));

    content.setSynopsis(synopsis);
    content.setDirector(director);

    content.setDuration(Integer.parseInt(duration));
    content.setYear(Integer.parseInt(year));
    content.setIdIMDB(idIMDB);
    content.setSeasonsNumber(Integer.parseInt(seasonsNumber));
    content.setEpisodesNumber(Integer.parseInt(episodesNumber));
    content.setTotalEpisodesNumber(Integer.parseInt(totalEpisodesNumber));

    if (genres != null) {
      content.setGenres(Arrays.stream(new String[]{genres})
              .map(ContentGenres::valueOf)
              .toArray(ContentGenres[]::new));
    }

    if (streamingPlatforms != null) {
      content.setStreamingPlatforms(Arrays.stream(new String[]{streamingPlatforms})
              .map(ContentStreamingPlatforms::valueOf)
              .toArray(ContentStreamingPlatforms[]::new));
    }

    // Chiamare il service per salvare il contenuto
    //contentService.addContent(content);


    // Redirigi a una pagina di conferma
    return "redirect:/admin/addcontent";
  }


}
