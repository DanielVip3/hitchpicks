package it.unisa.hitchpicks.storage;

import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

/**
 * Represents a Content entity.
 */
@Getter
@Entity
public class Content {
  @Id
  @GeneratedValue
  private Integer id;

  @NotNull(message = "Image URL must not be empty.")
  @NotEmpty(message = "Image URL must not be empty.")
  @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)",
    message = "Image URL must have a valid format.")
  @Length(max = 2000, message = "Image URL must be at most 2000 characters.")
  @Column(length = 2000)
  @Setter private String image;

  @NotNull(message = "Title must not be empty.")
  @NotEmpty(message = "Title must not be empty.")
  @Length(max = 100, message = "Title must be at most 100 characters.")
  @Column(length = 100)
  @Setter private String title;

  @NotNull(message = "Content type must not be empty.")
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "content_type")
  @ColumnTransformer(write = "?::content_type")
  @Setter private ContentType type;

  @NotNull(message = "Status must not be empty.")
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "content_state")
  @ColumnTransformer(write = "?::content_state")
  @Setter private ContentState state;

  @Length(max = 1000, message = "Synopsis must be at most 1000 characters.")
  @Column(length = 1000)
  @Setter private String synopsis;

  @Length(max = 100, message = "Director must be at most 100 characters.")
  @Column(length = 100)
  @Setter private String director;

  @Min(value = 1, message = "Duration must be at least 1 minute.")
  @Max(value = 1000000, message = "Duration must be at most 1000000 minutes.")
  @Setter private Integer duration;

  @Min(value = 1900, message = "Year must be 1900 or following.")
  @Setter private Integer year;

  @Pattern(regexp = "^$|^[A-Za-z]{2}\\d{7}$", message = "IMDB ID must have a valid format.")
  @Setter private String idIMDB;

  @Min(value = 1, message = "Seasons must be at least 1.")
  @Max(value = 1000, message = "Seasons must be at most 1000.")
  @Setter private Integer seasonsNumber;

  @Min(value = 1, message = "Current episodes must be at least 1.")
  @Max(value = 100000, message = "Current episodes must be at most 100000.")
  @Setter private Integer episodesNumber;

  @Min(value = 1, message = "Total episodes must be at least 1.")
  @Max(value = 100000, message = "Total episodes must be at most 100000.")
  @Setter private Integer totalEpisodesNumber;

  @Type(
      value = EnumArrayType.class,
      parameters = @Parameter(
          name = AbstractArrayType.SQL_ARRAY_TYPE,
          value = "content_genre"
      )
  )
  @Column(columnDefinition = "content_genre[]")
  @Setter private ContentGenres[] genres;

  @Type(
      value = EnumArrayType.class,
      parameters = @Parameter(
          name = AbstractArrayType.SQL_ARRAY_TYPE,
          value = "streaming_platform"
      )
  )
  @Column(columnDefinition = "streaming_platform[]")
  @Setter private ContentStreamingPlatforms[] streamingPlatforms;
}
