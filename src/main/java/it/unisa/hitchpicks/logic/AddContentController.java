package it.unisa.hitchpicks.logic;

import it.unisa.hitchpicks.storage.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            @RequestParam(value = "director", required = false) String director,
            @RequestParam(value = "duration", required = false) String duration,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "idIMDB", required = false) String idIMDB,
            @RequestParam(value = "seasonsNumber", required = false) String seasonsNumber,
            @RequestParam(value = "episodesNumber", required = false) String episodesNumber,
            @RequestParam(value = "totalEpisodesNumber", required = false) String totalEpisodesNumber,
            @RequestParam(value = "genres", required = false) String genres,
            @RequestParam(value = "streamingPlatforms", required = false) String streamingPlatforms,
            RedirectAttributes redirectAttributes
    ) {
        try {
            //Aggiungere matches su url
            if (image == null || image.isEmpty() || image.length() > 2000) {
                throw new IllegalArgumentException("Campo 'image' non valido");
            }

            if (title == null || title.isEmpty() || title.length() > 100) {
                throw new IllegalArgumentException("Campo 'title' non valido");
            }

            if (synopsis == null || synopsis.isEmpty() || synopsis.length() > 1000) {
                throw new IllegalArgumentException("Campo 'synopsis' non valido");
            }


            ContentType contentType = ContentType.valueOf(type.toUpperCase());
            ContentState contentState = ContentState.valueOf(state.toUpperCase());


            Content content = new Content();
            content.setImage(image);
            content.setTitle(title);
            content.setType(contentType);
            content.setState(contentState);
            content.setSynopsis(synopsis);


            if (director != null && director.length() <= 100) {
                content.setDirector(director);
            }

            if (duration != null) {
                int durationValue = Integer.parseInt(duration);
                if (durationValue > 0 && durationValue <= 1000000) {
                    content.setDuration(durationValue);
                } else {
                    throw new IllegalArgumentException("Campo 'duration' non valido");
                }
            }

            if (year != null) {
                int yearValue = Integer.parseInt(year);
                int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                if (yearValue >= 1900 && yearValue <= currentYear + 10) {
                    content.setYear(yearValue);
                } else {
                    throw new IllegalArgumentException("Campo 'year' non valido");
                }
            }

           /* if (idIMDB != null) {
                if (idIMDB.matches("^tt\\d{7,8}$")) {
                    content.setIdIMDB(idIMDB);
                } else {
                    throw new IllegalArgumentException("Campo 'idIMDB' non valido");
                }
            }*/

            if (seasonsNumber != null) {
                int seasonsValue = Integer.parseInt(seasonsNumber);
                if (seasonsValue > 0 && seasonsValue <= 1000) {
                    content.setSeasonsNumber(seasonsValue);
                } else {
                    throw new IllegalArgumentException("Campo 'seasonsNumber' non valido");
                }
            }

            if (episodesNumber != null && totalEpisodesNumber != null) {
                int episodesValue = Integer.parseInt(episodesNumber);
                int totalEpisodesValue = Integer.parseInt(totalEpisodesNumber);
                if (episodesValue > 0 && episodesValue <= 100000 && episodesValue <= totalEpisodesValue) {
                    content.setEpisodesNumber(episodesValue);
                } else {
                    throw new IllegalArgumentException("Campo 'episodesNumber' non valido");
                }
            }

            if (totalEpisodesNumber != null) {
                int totalEpisodesValue = Integer.parseInt(totalEpisodesNumber);
                if (totalEpisodesValue > 0 && totalEpisodesValue <= 100000) {
                    content.setTotalEpisodesNumber(totalEpisodesValue);
                } else {
                    throw new IllegalArgumentException("Campo 'totalEpisodesNumber' non valido");
                }
            }

            // Gestione dei generi
            if (genres != null) {
                try {
                    content.setGenres(Arrays.stream(genres.split(","))
                            .map(String::trim)
                            .map(ContentGenres::valueOf)
                            .toArray(ContentGenres[]::new));
                } catch (IllegalArgumentException e) {
                    // Errore di parsing dei generi
                    System.out.println("Errore: uno dei valori di genere non è valido.");
                }
            }

            // Gestione delle piattaforme di streaming
            if (streamingPlatforms != null) {
                try {
                    content.setStreamingPlatforms(Arrays.stream(streamingPlatforms.split(","))
                            .map(String::trim)
                            .map(ContentStreamingPlatforms::valueOf)
                            .toArray(ContentStreamingPlatforms[]::new));
                } catch (IllegalArgumentException e) {
                    // Errore di parsing delle piattaforme di streaming
                    System.out.println("Errore: uno dei valori di piattaforma di streaming non è valido.");
                }
            }

            // Creazione del contenuto
            contentService.create(content);

            return "redirect:/admin/addcontent";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/addcontent";
        }
    }

}
