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

  @NotNull
  @NotEmpty
  @Pattern(regexp = "((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)")
  @Length(max = 2000)
  @Column(length = 2000)
  @Setter private String image;

  @NotNull
  @NotEmpty
  @Length(max = 100)
  @Column(length = 100)
  @Setter private String title;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "content_type")
  @ColumnTransformer(write = "?::content_type")
  @Setter private ContentType type;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "content_state")
  @ColumnTransformer(write = "?::content_state")
  @Setter private ContentState state;

  @Length(max = 1000)
  @Column(length = 1000)
  @Setter private String synopsis;

  @Length(max = 100)
  @Column(length = 100)
  @Setter private String director;

  @Min(1)
  @Max(1000000)
  @Setter private Integer duration;

  @Min(1900)
  @Setter private Integer year;

  @Pattern(regexp = "^$|^[A-Za-z]{2}\\d{7}$")
  @Setter private String idIMDB;

  @Min(1)
  @Max(1000)
  @Setter private Integer seasonsNumber;

  @Min(1)
  @Max(100000)
  @Setter private Integer episodesNumber;

  @Min(1)
  @Max(100000)
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
