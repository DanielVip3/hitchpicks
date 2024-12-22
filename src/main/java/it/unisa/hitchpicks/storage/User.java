package it.unisa.hitchpicks.storage;

import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

/**
 * Represents a User entity.
 */
@Getter
@Entity
@Table(name = "user_table")
public class User {
  @Id
  @GeneratedValue
  private Integer id;

  @NotNull
  @Length(min = 3, max = 25)
  @Column(length = 25)
  @Setter private String name;

  @NotNull
  @Length(max = 320)
  @Column(length = 320)
  @Setter private String email;

  @NotNull
  @Length(min = 8, max = 255)
  @Column(length = 255)
  @Setter private String password;

  @Length(max = 25)
  @Column(length = 25)
  @Setter private String tag;

  @NotNull
  @Setter private Boolean admin;
}
