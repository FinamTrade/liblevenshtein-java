package com.github.liblevenshtein.distance;

import com.github.liblevenshtein.collection.SymmetricImmutablePair;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Common, initialization logic for memoized, distance metrics.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class AbstractMemoized implements IDistance<String>, Serializable {

  /** Default return value of memoized distances when no record exists. */
  protected static final int DEFAULT_RETURN_VALUE = -1;

  private static final long serialVersionUID = 1L;

  /** Memoizes the distance pairs of terms. */
  protected final Map<SymmetricImmutablePair<String>,Integer> memo;

  /**
   * Initializes the memoization map, etc.
   */
  public AbstractMemoized() {
    memo = new HashMap<>();
  }

  /**
   * If {@code t} is less than the length of {@code u}, then return the
   * substring of {@code u} beginning at {@code 1 + t}; Otherwise, return an
   * empty string. It is assumed that {@code u} and {@code v} are not null.
   * @param u String to slice
   * @param t Lower bound of the index at which to slice {@code u}
   * @return The substring of {@code u} beginning at index, {@code 1 + t}, if
   *   {@code t} is less than the length of {@code u}, or an empty string
   *   otherwise.
   */
  public String f(final String u, final int t) {
    if (t < u.length()) {
      return u.substring(1 + t);
    }

    return "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized int between(final String v, final String w) {
    // Don't want to check for nullity on each recursion ...
    return memoizedDistance(v, w);
  }

  /**
   * Memoizes the distance between terms, {@code v} and {@code w}, and returns
   * it.
   * @param v Term to compare with {@code w}
   * @param w Term to compare with {@code v}
   * @return Distance between {@code v} and {@code w}
   */
  public abstract int memoizedDistance(String v, String w);
}
