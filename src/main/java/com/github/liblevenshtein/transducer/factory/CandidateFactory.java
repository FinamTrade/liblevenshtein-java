package com.github.liblevenshtein.transducer.factory;

import com.github.liblevenshtein.transducer.Candidate;

import java.io.Serializable;

/**
 * Builds spelling candidates of the requested type, optionally including the
 * distance of the candidate from the query term.
 * @author Dylon Edwards
 * @param <CandidateType> Kind of spelling candidate built by this factory.
 * @since 2.1.2
 */
public abstract class CandidateFactory<CandidateType> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds a new spelling candidate from the dictionary term and its
   * Levenshtein distance from the query term.
   * @param term Candidate term from the dictionary.
   * @param distance Levenshtein distance of the dictionary term from the query
   *   term.
   * @return A new spelling candidate, optionally with the distance included.
   */
  public abstract CandidateType build(String term, int distance);

  /**
   * Builds instances of {@link Candidate}, with the dictionary term and its
   * Levenshtein distance from the query term.
   * @author Dylon Edwards
   * @since 2.1.2
   */
  public static class WithDistance extends CandidateFactory<Candidate> {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Candidate build(final String term, final int distance) {
      return new Candidate(term, distance);
    }
  }

  /**
   * Does not include the Levenshtein distance, but returns the dictionary term
   * alone.
   * @author Dylon Edwards
   * @since 2.1.2
   */
  public static class WithoutDistance extends CandidateFactory<String> {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public String build(final String term, final int distance) {
      return term;
    }
  }
}
