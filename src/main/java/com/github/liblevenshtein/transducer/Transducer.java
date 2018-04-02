package com.github.liblevenshtein.transducer;

import java.io.Serializable;
import java.util.Objects;

/**
 * This wrapper around {@link LazyTransducerCollection}, which handles all the
 * heavy lifting.
 *
 * @author Dylon Edwards
 * @param <DictionaryNode> Kind of nodes of the dictionary automaton.
 * @param <CandidateType> Kind of the spelling candidates returned from the
 *   dictionary.
 * @since 2.1.0
 */
public class Transducer<DictionaryNode, CandidateType>
  implements ITransducer<CandidateType>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Attributes required for this transducer to search the dictionary.
   */
  private TransducerAttributes<DictionaryNode, CandidateType> attributes;

  public Transducer(TransducerAttributes<DictionaryNode, CandidateType> attributes) {
    this.attributes = attributes;
  }

  public TransducerAttributes<DictionaryNode, CandidateType> attributes() {
    return attributes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<CandidateType> transduce(final String term) {
    return transduce(term, attributes.maxDistance());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<CandidateType> transduce(
      final String term,
      final int maxDistance) {
    return new LazyTransducerCollection<DictionaryNode, CandidateType>(
        term, maxDistance, attributes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transducer<?, ?> that = (Transducer<?, ?>) o;
    return Objects.equals(attributes, that.attributes);
  }

  @Override
  public int hashCode() {

    return Objects.hash(attributes);
  }
}
