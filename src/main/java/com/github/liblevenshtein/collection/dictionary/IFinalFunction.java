package com.github.liblevenshtein.collection.dictionary;

import java.io.Serializable;

/**
 * Specifies the necessary methods for functors that return whether nodes of
 * some type represent the final states of an automaton.
 * @author Dylon Edwards
 * @param <State> Kind of the state this function accepts.
 * @since 2.1.0
 */
public interface IFinalFunction<State> extends Serializable {

  /**
   * Whether the state represents the last character of some term.
   * @param current State to check for finality.
   * @return Whether this node represents the last character of some term.
   */
  boolean at(State current);
}
