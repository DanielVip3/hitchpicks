package it.unisa.hitchpicks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Todo /* extends PanacheEntity */ {
  @Id
  public String task;
  public Date completed;

  // Mocking of existing data, this would normally be in your DB and go via Hibernate/Panache
  private static final List<Todo> all = new ArrayList<>();

  public static List<Todo> listAll() {
    return all;
  }

  public void persist() {
    all.add(this);
  }
}