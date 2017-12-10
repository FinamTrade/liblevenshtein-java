package com.github.liblevenshtein.collection.dictionary;

import java.io.Serializable;
import java.util.Objects;

/**
 * Links two nodes together, under a character label.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class Transition implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Node from which the transition is leaving.
   */
  private DawgNode source;

  /**
   * Label mapping {@link #source} to {@link #target}.
   */
  private char label;

  /**
   * Node to which the transition is going.
   */
  private DawgNode target;

  public Transition(DawgNode source, char label, DawgNode target) {
    this.source = source;
    this.label = label;
    this.target = target;
  }

  public DawgNode source() {
    return source;
  }

  public char label() {
    return label;
  }

  public DawgNode target() {
    return target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transition that = (Transition) o;
    return label == that.label &&
            Objects.equals(source, that.source) &&
            Objects.equals(target, that.target);
  }

  @Override
  public int hashCode() {

    return Objects.hash(source, label, target);
  }
}
