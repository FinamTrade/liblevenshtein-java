package com.github.liblevenshtein.transducer;

import java.io.Serializable;

/**
 * {@link SpecialPosition}s are used to maintain a sorted, linked-list of
 * positions within {@link com.github.liblevenshtein.transducer.State}s.  They
 * are sorted to simplify and optimize various operations on the positions (like
 * subsumption and merging-in new positions).
 * @author Dylon Edwards
 * @since 3.0.0
 */
public class SpecialPosition extends Position implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds a new {@link Position}, extended with some special operation (e.g.
   * transposition or merge-and-split).
   * @param termIndex Index of the dictionary term represented by this coordinate.
   * @param numErrors Number of accumulated errors at this coordinate.
   */
  public SpecialPosition(final int termIndex, final int numErrors) {
    super(termIndex, numErrors);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
