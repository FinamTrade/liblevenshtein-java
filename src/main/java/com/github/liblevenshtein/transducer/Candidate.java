package com.github.liblevenshtein.transducer;

import java.io.Serializable;
import java.util.Objects;

/**
 * POJO returned when the distances are requested of the candidate terms from
 * the query term, that are returned from the transducer.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class Candidate implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Candidate term from the dictionary automaton.
   */
  private final String term;

  /**
   * Distance between the candidate term and the query term.
   */
  private final int distance;

  public Candidate(String term, int distance) {
    this.term = term;
    this.distance = distance;
  }

  public String term() {
    return term;
  }

  public int distance() {
    return distance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Candidate candidate = (Candidate) o;
    return distance == candidate.distance &&
            Objects.equals(term, candidate.term);
  }

  @Override
  public int hashCode() {

    return Objects.hash(term, distance);
  }
}
