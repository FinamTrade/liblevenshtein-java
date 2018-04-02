package com.github.liblevenshtein.collection.dictionary.factory;

import com.github.liblevenshtein.collection.dictionary.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Constructs DAWG instances.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class DawgFactory implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Returns a new DAWG.
   * @param terms Terms to insert into the DAWG
   * @return A new DAWG, containing the terms.
   */
  public Dawg build(final Collection<String> terms) {
    return build(terms, false);
  }

  /**
   * Returns a new DAWG.
   * @param terms Terms to insert into the DAWG
   * @param isSorted Whether terms has been sorted
   * @return A new DAWG, containing the terms.
   */
  public Dawg build(
      final Collection<String> terms,
      final boolean isSorted) {

    if (terms instanceof SortedDawg) {
      return (SortedDawg) terms;
    }

    if (!isSorted) {
      if (!(terms instanceof List)) {
        return build(new ArrayList<String>(terms), false);
      }

      Collections.sort((List<String>) terms);
    }

    return new SortedDawg(terms);
  }

  /**
   * Returns the final function of the dictionary.
   * @param dictionary Dawg whose final function should be returned
   * @return The final function of the dictionary
   */
  public IFinalFunction<DawgNode> finalFunction(final Dawg dictionary) {
    return dictionary;
  }

  /**
   * Returns the transition function of the dictionary.
   * @param dictionary Dawg whose transition function should be returned
   * @return The transition function of the dictionary
   */
  public ITransitionFunction<DawgNode> transitionFunction(final Dawg dictionary) {
    return dictionary;
  }
}
