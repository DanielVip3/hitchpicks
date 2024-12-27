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
            // Add matches on url
            if (image == null || image.isEmpty() || image.length() > 2000 || !image.matches("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)")) {
                throw new IllegalArgumentException("Invalid 'image' field");
            }

            if (title == null || title.isEmpty() || title.length() > 100) {
                throw new IllegalArgumentException("Invalid 'title' field");
            }

            if (synopsis == null || synopsis.isEmpty() || synopsis.length() > 1000) {
                throw new IllegalArgumentException("Invalid 'synopsis' field");
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

            if (duration != null && !duration.equals("0")) {
                int durationValue = Integer.parseInt(duration);
                if (durationValue > 0 && durationValue <= 1000000) {
                    content.setDuration(durationValue);
                } else {
                    throw new IllegalArgumentException("Invalid 'duration' field");
                }
            }

            if (year != null && !year.equals("0")) {
                int yearValue = Integer.parseInt(year);
                int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                if (yearValue >= 1900 && yearValue <= currentYear + 10) {
                    content.setYear(yearValue);
                } else {
                    throw new IllegalArgumentException("Invalid 'year' field");
                }
            }

            if (idIMDB != null) {
                if (idIMDB.matches("^[A-Za-z]{2}\\d{7}$")) {
                    content.setIdIMDB(idIMDB);
                } else {
                    throw new IllegalArgumentException("Invalid 'idIMDB' field: it must start with two letters followed by 7 digits");
                }
            }

            if (seasonsNumber != null) {
                int seasonsValue = Integer.parseInt(seasonsNumber);
                if (seasonsValue > 0 && seasonsValue <= 1000) {
                    content.setSeasonsNumber(seasonsValue);
                } else {
                    throw new IllegalArgumentException("Invalid 'seasonsNumber' field");
                }
            }

            if (episodesNumber != null && totalEpisodesNumber != null) {
                int episodesValue = Integer.parseInt(episodesNumber);
                int totalEpisodesValue = Integer.parseInt(totalEpisodesNumber);
                if (episodesValue > 0 && episodesValue <= 100000 && episodesValue <= totalEpisodesValue) {
                    content.setEpisodesNumber(episodesValue);
                } else {
                    throw new IllegalArgumentException("Invalid 'episodesNumber' field");
                }
            }

            if (totalEpisodesNumber != null) {
                int totalEpisodesValue = Integer.parseInt(totalEpisodesNumber);
                if (totalEpisodesValue > 0 && totalEpisodesValue <= 100000) {
                    content.setTotalEpisodesNumber(totalEpisodesValue);
                } else {
                    throw new IllegalArgumentException("Invalid 'totalEpisodesNumber' field");
                }
            }

            // Manage genres
            if (genres != null) {
                try {
                    content.setGenres(Arrays.stream(genres.split(","))
                            .map(String::trim)
                            .map(ContentGenres::valueOf)
                            .toArray(ContentGenres[]::new));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid 'genres' field");
                }
            }

            if (streamingPlatforms != null) {
                try {
                    content.setStreamingPlatforms(Arrays.stream(streamingPlatforms.split(","))
                            .map(String::trim)
                            .map(ContentStreamingPlatforms::valueOf)
                            .toArray(ContentStreamingPlatforms[]::new));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid 'streamingPlatforms' field");
                }
            }

            contentService.create(content);

            return "redirect:/admin/addcontent";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/addcontent";
        }
    }

}