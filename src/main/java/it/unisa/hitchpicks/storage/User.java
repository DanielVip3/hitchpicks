package it.unisa.hitchpicks.storage;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * Represents a User entity.
 */
@Getter
@Entity
@Table(name = "user_table")
public class User implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

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
