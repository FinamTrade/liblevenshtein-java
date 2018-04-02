package com.github.liblevenshtein.transducer;

import com.github.liblevenshtein.collection.dictionary.Dawg;
import com.github.liblevenshtein.collection.dictionary.IFinalFunction;
import com.github.liblevenshtein.collection.dictionary.ITransitionFunction;
import com.github.liblevenshtein.transducer.factory.CandidateFactory;
import com.github.liblevenshtein.transducer.factory.StateTransitionFactory;

import java.io.Serializable;
import java.util.Objects;

/**
 * Attributes required for this transducer to search the dictionary.
 * @author Dylon Edwards
 * @since 2.1.2
 * @param <DictionaryNode> Kind of nodes of the dictionary automaton.
 * @param <CandidateType> Kind of the spelling candidates returned from the
 */
public class TransducerAttributes<DictionaryNode, CandidateType> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TransducerAttributes<?, ?> that = (TransducerAttributes<?, ?>) o;
    return maxDistance == that.maxDistance &&
            includeDistance == that.includeDistance &&
            Objects.equals(dictionary, that.dictionary) &&
            algorithm == that.algorithm;
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxDistance, dictionary, algorithm, includeDistance);
  }

  /**
   * Maximum number of spelling errors candidates may have from the query term.
   */
  protected int maxDistance = Integer.MAX_VALUE;

  /**
   * Generates spelling candidates of the requested type. The candidates  which
   * may optionally include the Levenshtein distance between their dictionary
   * terms and the query term.
   */
  protected CandidateFactory<CandidateType> candidateFactory;

  /**
   * Returns state-transition functions for specific, max edit distances.
   */
  protected StateTransitionFactory stateTransitionFactory;

  /**
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   */
  protected DistanceFunction minDistance;

  /**
   * Returns whether a dictionary node is the final character in some term.
   */
  protected IFinalFunction<DictionaryNode> isFinal;

  /**
   * Transition function for dictionary nodes.
   */
  protected ITransitionFunction<DictionaryNode> dictionaryTransition;

  /**
   * State at which to begin traversing the Levenshtein automaton.
   */
  protected State initialState;

  /**
   * Root node of the dictionary, at which to begin searching for spelling
   * candidates.
   */
  protected DictionaryNode dictionaryRoot;

  /**
   * Dictionary of this transducer.
   */
  protected Dawg dictionary;

  /**
   * Transduction algorithm.
   */
  protected Algorithm algorithm;

  /**
   * Whether to include the number of errors from the query term with the
   * candidate terms.
   */
  protected boolean includeDistance;

  public int maxDistance() {
    return maxDistance;
  }

  public CandidateFactory<CandidateType> candidateFactory() {
    return candidateFactory;
  }

  public StateTransitionFactory stateTransitionFactory() {
    return stateTransitionFactory;
  }

  public DistanceFunction minDistance() {
    return minDistance;
  }

  public IFinalFunction<DictionaryNode> isFinal() {
    return isFinal;
  }

  public ITransitionFunction<DictionaryNode> dictionaryTransition() {
    return dictionaryTransition;
  }

  public State initialState() {
    return initialState;
  }

  public DictionaryNode dictionaryRoot() {
    return dictionaryRoot;
  }

  public Dawg dictionary() {
    return dictionary;
  }

  public Algorithm algorithm() {
    return algorithm;
  }

  public boolean includeDistance() {
    return includeDistance;
  }

  public TransducerAttributes maxDistance(int maxDistance) {
    this.maxDistance = maxDistance;
    return this;
  }

  public TransducerAttributes candidateFactory(CandidateFactory<CandidateType> candidateFactory) {
    this.candidateFactory = candidateFactory;
    return this;
  }

  public TransducerAttributes stateTransitionFactory(StateTransitionFactory stateTransitionFactory) {
    this.stateTransitionFactory = stateTransitionFactory;
    return this;
  }

  public TransducerAttributes minDistance(DistanceFunction minDistance) {
    this.minDistance = minDistance;
    return this;
  }

  public TransducerAttributes isFinal(IFinalFunction<DictionaryNode> isFinal) {
    this.isFinal = isFinal;
    return this;
  }

  public TransducerAttributes dictionaryTransition(ITransitionFunction<DictionaryNode> dictionaryTransition) {
    this.dictionaryTransition = dictionaryTransition;
    return this;
  }

  public TransducerAttributes initialState(State initialState) {
    this.initialState = initialState;
    return this;
  }

  public TransducerAttributes dictionaryRoot(DictionaryNode dictionaryRoot) {
    this.dictionaryRoot = dictionaryRoot;
    return this;
  }

  public TransducerAttributes dictionary(Dawg dictionary) {
    this.dictionary = dictionary;
    return this;
  }

  public TransducerAttributes algorithm(Algorithm algorithm) {
    this.algorithm = algorithm;
    return this;
  }

  public TransducerAttributes includeDistance(boolean includeDistance) {
    this.includeDistance = includeDistance;
    return this;
  }
}
