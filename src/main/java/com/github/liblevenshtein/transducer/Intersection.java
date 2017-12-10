package com.github.liblevenshtein.transducer;

import java.io.Serializable;
import java.util.Objects;

/**
 * State along the intersection of the dictionary automaton and the Levenshtein
 * automaton.
 * @param <DictionaryNode> Kind of node in the dictionary automaton.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class Intersection<DictionaryNode> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Intersection along the prefix from the root, dictionary node to the one
   * prior to {@link dictionaryNode}.
   */
  private final Intersection<DictionaryNode> prevIntersection;

  /**
   * Label annotating the edge between the previous dictionary node and
   * {@link #dictionaryNode}.
   */
  private final char label;

  /**
   * Current node in the dictionary, along the intersection's path.
   */
  private final DictionaryNode dictionaryNode;

  /**
   * Current node in the Levenshtein automaton, along the intersection's path.
   */
  private final State levenshteinState;

  /**
   * Constructs an intersection representing the start states of both the
   * dictionary and Levenshtein automata.
   * @param dictionaryRoot Root node of the dictionary automaton.
   * @param initialState Initial state of the Levenshtein automaton.
   */
  public Intersection(
      final DictionaryNode dictionaryRoot,
      final State initialState) {
    this(null, '\0', dictionaryRoot, initialState);
  }

  public Intersection(Intersection<DictionaryNode> prevIntersection, char label, DictionaryNode dictionaryNode, State levenshteinState) {
    this.prevIntersection = prevIntersection;
    this.label = label;
    this.dictionaryNode = dictionaryNode;
    this.levenshteinState = levenshteinState;
  }

  public Intersection<DictionaryNode> prevIntersection() {
    return prevIntersection;
  }

  public char label() {
    return label;
  }

  public DictionaryNode dictionaryNode() {
    return dictionaryNode;
  }

  public State levenshteinState() {
    return levenshteinState;
  }

  /**
   * Spelling candidate from the dictionary automaton, represented as the prefix
   * of a term in the dictionary constructed by traversing from its root to
   * dictionaryNode, and collecting the characters along the way.
   * @return Spelling candidate from the dictionary automaton.
   */
  public String candidate() {
    return buffer().toString();
  }

  /**
   * Buffers the prefix built by traversion the path from the root node to
   * {@link dictionaryNode}.
   * @return The prefix from the root node to {@link dictionaryNode}.
   */
  private StringBuilder buffer() {
    final StringBuilder buffer;

    if (prevIntersection != null) {
      buffer = prevIntersection.buffer();
      buffer.append(label);
    }
    else {
      buffer = new StringBuilder();
    }

    return buffer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Intersection<?> that = (Intersection<?>) o;
    return label == that.label &&
            Objects.equals(prevIntersection, that.prevIntersection) &&
            Objects.equals(dictionaryNode, that.dictionaryNode) &&
            Objects.equals(levenshteinState, that.levenshteinState);
  }

  @Override
  public int hashCode() {

    return Objects.hash(prevIntersection, label, dictionaryNode, levenshteinState);
  }
}
