package com.github.liblevenshtein.collection.dictionary;

import com.github.liblevenshtein.collection.AbstractIterator;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Iterates over the terms within an {@link Dawg}.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class DawgIterator extends AbstractIterator<String> {

  private static final long serialVersionUID = 1L;

  /**
   * Queue for traversing the terms in the {@link Dawg} in a
   * depth-first search manner.
   */
  private final Queue<Prefix> prefixes = new ArrayDeque<>();

  /**
   * Returns whether the current {@link DawgNode} represents the last character
   * in some term.
   */
  private final IFinalFunction<DawgNode> isFinal;

  /**
   * Initializes a new {@link DawgIterator}.
   * @param root Root of the DAWG structure to traverse
   * @param isFinal Returns whether some {@link DawgNode} represents the last
   *   character in some term.
   */
  public DawgIterator(
      final DawgNode root,
      final IFinalFunction<DawgNode> isFinal) {
    this.isFinal = isFinal;
    prefixes.offer(new Prefix(root));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void advance() {
    if (null == next && !prefixes.isEmpty()) {
      DawgNode node;
      String value;

      do {
        final Prefix prefix = prefixes.poll();
        node = prefix.node();
        value = prefix.value();
        final Iterator<Character> iter = node.labels();
        while (iter.hasNext()) {
          final char label = iter.next();
          final DawgNode nextNode = node.transition(label);
          prefixes.add(new Prefix(nextNode, prefix, label));
        }
      }
      while (!isFinal.at(node));
      this.next = value;
    }
  }
}
