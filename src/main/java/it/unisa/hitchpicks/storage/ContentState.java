package it.unisa.hitchpicks.storage;

/**
 * Represents a content's releasing state.
 * TBA means "to be announced"; announced means that a vague date has been given;
 * ongoing means "currently releasing" (e.g. weekly, monthly); released means "finished".
 * Movies usually don't go through the ongoing state.
 */
public enum ContentState {
  TBA,
  ANNOUNCED,
  ONGOING,
  RELEASED
}
