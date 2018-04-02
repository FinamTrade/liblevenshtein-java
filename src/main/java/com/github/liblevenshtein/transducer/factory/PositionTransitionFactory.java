package com.github.liblevenshtein.transducer.factory;

import com.github.liblevenshtein.transducer.MergeAndSplitPositionTransitionFunction;
import com.github.liblevenshtein.transducer.PositionTransitionFunction;
import com.github.liblevenshtein.transducer.StandardPositionTransitionFunction;
import com.github.liblevenshtein.transducer.TranspositionPositionTransitionFunction;

import java.io.Serializable;

/**
 * Builds position-transition functions for Levenshtein states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class PositionTransitionFactory implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds Levenshtein states.
   */
  protected StateFactory stateFactory;

  /**
   * Builds Levenshtein positions.
   */
  protected PositionFactory positionFactory;

  public PositionTransitionFactory stateFactory(StateFactory stateFactory) {
    this.stateFactory = stateFactory;
    return this;
  }

  public PositionTransitionFactory positionFactory(PositionFactory positionFactory) {
    this.positionFactory = positionFactory;
    return this;
  }

  /**
   * Builds a new position-transition function.
   * @return New position-transition function.
   */
  public abstract PositionTransitionFunction build();

  /**
   * Builds position-transition functions for standard, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForStandardPositions extends PositionTransitionFactory {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public PositionTransitionFunction build() {
      return new StandardPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }

  /**
   * Builds position-transition functions for transposition, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForTranspositionPositions extends PositionTransitionFactory {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public PositionTransitionFunction build() {
      return new TranspositionPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }

  /**
   * Builds position-transition functions for merge-and-split, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForMergeAndSplitPositions extends PositionTransitionFactory {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public PositionTransitionFunction build() {
      return new MergeAndSplitPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }
}
