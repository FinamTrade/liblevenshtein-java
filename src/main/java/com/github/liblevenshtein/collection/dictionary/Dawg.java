package com.github.liblevenshtein.collection.dictionary;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Provides common logic for all my Dawg implementations.  Currently, there is
 * only the {@link SortedDawg} implementation, but I have plans for other kinds.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class Dawg
    extends AbstractSet<String>
    implements IFinalFunction<DawgNode>,
               ITransitionFunction<DawgNode>,
               Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Root node of this trie.
   */
  protected DawgNode root = null;

  /**
   * Number of terms in this trie.
   */
  protected int size = 0;

  /**
   * Initializes an {@link Dawg}.
   * @param root Root node of this DAWG.
   * @param size Number of terms in the dictionary.
   */
  protected Dawg(
      final DawgNode root,
      final int size) {
    this.root = root;
    this.size = size;
  }

  /**
   * Initializes an {@link Dawg}.
   */
  public Dawg() {
    this(new DawgNode(), 0);
  }

  public DawgNode root() {
    return root;
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean at(final DawgNode node) {
    return node.isFinal();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode of(final DawgNode node, final char label) {
    return node.transition(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Character> of(final DawgNode node) {
    return node.labels();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract boolean add(final String term);

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized boolean addAll(final Collection<? extends String> terms) {
    for (final String term : terms) {
      if (!add(term)) {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract boolean remove(final Object term);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final Object o) {
    if (!(o instanceof String)) {
      return false;
    }
    @SuppressWarnings("unchecked")
    final String term = (String) o;
    DawgNode node = root;
    for (int i = 0; i < term.length() && null != node; ++i) {
      final char label = term.charAt(i);
      node = node.transition(label);
    }
    return null != node && node.isFinal();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<String> iterator() {
    return new DawgIterator(root, this);
  }

  /**
   * [Optional Operation] Replaces the String, current, with another.
   * @param current String in this DAWG to replace
   * @param replacement String to replace the current one with
   * @return Whether the replacement was successful.
   */
  public boolean replace(final String current, final String replacement) {
    throw new UnsupportedOperationException("replace is not supported");
  }

  /**
   * [Optional Operation] Replaces all instances of the term keys with their
   * values.
   * @param c Replacment mappings.
   * @return Whether all replacements were successful.
   */
  public boolean replaceAll(final Collection<? extends Map.Entry<String, String>> c) {
    throw new UnsupportedOperationException("replaceAll is not supported");
  }

  @Override
  public String toString() {
    return String.format("%s(size=%d)", getClass().getSimpleName(), size);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Dawg strings = (Dawg) o;
    return size == strings.size &&
            Objects.equals(root, strings.root);
  }

  @Override
  public int hashCode() {

    return Objects.hash(root, size);
  }
}
