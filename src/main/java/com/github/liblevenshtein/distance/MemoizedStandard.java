package com.github.liblevenshtein.distance;

import com.github.liblevenshtein.collection.SymmetricImmutablePair;

/**
 * Memoizes the distance between two terms, where the distance is calculated
 * using the standard Levenshtein distance, including the elementary operations
 * of insertion, deletion and substitution.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class MemoizedStandard extends AbstractMemoized {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("checkstyle:finalparameters")
  public int memoizedDistance(String v, String w) {
    SymmetricImmutablePair<String> key = new SymmetricImmutablePair<>(v, w);

    int distance = memo.getOrDefault(key, DEFAULT_RETURN_VALUE);
    if (distance != DEFAULT_RETURN_VALUE) {
      return distance;
    }

    if (v.isEmpty()) {
      distance = w.length();
      memo.put(key, distance);
      return distance;
    }

    if (w.isEmpty()) {
      distance = v.length();
      memo.put(key, distance);
      return distance;
    }

    char a = v.charAt(0); String s = v.substring(1);
    char b = w.charAt(0); String t = w.substring(1);

    // Discard identical characters
    while (a == b && s.length() > 0 && t.length() > 0) {
      a = s.charAt(0); v = s; s = v.substring(1);
      b = t.charAt(0); w = t; t = w.substring(1);
    }

    // s.length() == 0 or t.length() == 0
    if (a == b) {
      distance = s.isEmpty() ? t.length() : s.length();
      memo.put(key, distance);
      return distance;
    }

    distance = memoizedDistance(s, w);
    if (0 == distance) {
      memo.put(key, 1);
      return 1;
    }

    int minDistance = distance;

    distance = memoizedDistance(v, t);
    if (0 == distance) {
      memo.put(key, 1);
      return 1;
    }

    if (distance < minDistance) {
      minDistance = distance;
    }

    distance = memoizedDistance(s, t);
    if (0 == distance) {
      memo.put(key, 1);
      return 1;
    }

    if (distance < minDistance) {
      minDistance = distance;
    }

    distance = 1 + minDistance;
    memo.put(key, distance);
    return distance;
  }
}
