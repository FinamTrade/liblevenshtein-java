package com.github.liblevenshtein.transducer;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

/**
 * Transitions a standard, Levenshtein position to all possible positions, given
 * a set of parameters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StandardPositionTransitionFunction extends PositionTransitionFunction {

  private static final long serialVersionUID = 1L;

  public StandardPositionTransitionFunction stateFactory(StateFactory stateFactory) {
    this.stateFactory = stateFactory;
    return this;
  }

  public StandardPositionTransitionFunction positionFactory(PositionFactory positionFactory) {
    this.positionFactory = positionFactory;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public State of(
      final int n,
      final Position position,
      final boolean[] characteristicVector,
      final int offset) {

    final int i = position.termIndex();
    final int e = position.numErrors();
    final int h = i - offset;
    final int w = characteristicVector.length;

    // The edit distance is not below its maximum, allowed value.
    if (e < n) {
      // Consider any character before the last one of the spelling candidate
      if (h <= w - 2) {
        final int a = n - e < Integer.MAX_VALUE
          ? n - e + 1
          : Integer.MAX_VALUE;
        final int b = w - h;
        final int k = a < b ? a : b;
        final int j = indexOf(characteristicVector, k, h);

        if (0 == j) {
          return stateFactory.build(
              // [No Error]: Increment the index by one; leave the error alone.
              positionFactory.build(1 + i, e)
          );
        }

        if (j > 0) {
          return stateFactory.build(
              // [Insertion]: Leave the index alone; increment the error by one.
              positionFactory.build(i, e + 1),
              // [Substitution]: Increment both the index and error by one.
              positionFactory.build(i + 1, e + 1),
              // [Deletion]: Increment the index by one-more than the number of
              // deletions; increment the error by the number of deletions.
              positionFactory.build(i + j + 1, e + j)
          );
        }

        // else, j < 0
        return stateFactory.build(
            // [Insertion]: Leave the index alone; increment the error by one.
            positionFactory.build(i, e + 1),
            // [Substitution]: Increment both the index and error by one.
            positionFactory.build(i + 1, e + 1)
        );
      }

      // Consider the last character of the spelling candidate
      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment the index by one; leave the error alone.
              positionFactory.build(i + 1, e)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave the index alone; increment the error by one.
            positionFactory.build(i, e + 1),
            // [Substitution]: Increment both the index and error by one.
            positionFactory.build(i + 1, e + 1)
        );
      }

      // else, h == w
      return stateFactory.build(
          // [Insertion]: Leave the index alone; increment the error by one.
          positionFactory.build(i, e + 1)
      );
    }

    // The edit distance is at its maximum, allowed value.  Only consider this
    // spelling candidate if there is no error at the index of its current term.
    if (e == n && h <= w - 1 && characteristicVector[h]) {
      return stateFactory.build(
          // [No Error]: Increment the index by one; leave the error alone.
          positionFactory.build(i + 1, n)
      );
    }

    // [Too Many Errors]: The edit distance has exceeded the max distance for
    // the candidate term.
    return null;
  }
}
