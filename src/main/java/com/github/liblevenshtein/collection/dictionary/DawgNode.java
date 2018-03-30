package com.github.liblevenshtein.collection.dictionary;

import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.chars.CharIterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Non-final element of a DAWG structure (Directed Acyclic Word Graph).
 * Currently, this is tightly-coupled with character-node types.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class DawgNode implements Serializable {
  private static final String USE_FAST_MAPS = "com.github.liblevenshtein.use_fast_maps";

  private static final long serialVersionUID = 1L;
  private static final boolean shouldUseFastMaps;

  static {
    String s = System.getProperty(USE_FAST_MAPS);
    shouldUseFastMaps = s == null || (s = s.trim()).isEmpty() || Boolean.valueOf(s);
  }

  /**
   * Outgoing edges of this node.
   */
  protected final Map<Character,DawgNode> edges;

  /**
   * Constructs a non-final {@link DawgNode}.
   */
  public DawgNode() {
    this(newMap());
  }

  private static Map<Character,DawgNode> newMap() {
    return shouldUseFastMaps ? new Char2ObjectRBTreeMap<>() : new TreeMap<>();
  }

  public DawgNode(Map<Character,DawgNode> edges) {
    this.edges = edges;
  }

  /**
   * Specifies whether this node represents the last character of some term.
   * @return Whether this node represents the last character of some term.
   */
  public boolean isFinal() {
    return false;
  }

  /**
   * Returns the labels of the outgoing edges of this node.
   * @return Labels of the outgoing edges of this node.
   */
  public CharIterator labels() {
    Iterator<Character> iter = edges.keySet().iterator();
    if (iter instanceof CharIterator) {
      return (CharIterator) iter;
    } else {
      return new AbstractCharIterator() {
        final Iterator<Map.Entry<Character,DawgNode>> i = edges.entrySet().iterator();

        @Override
        public boolean hasNext() {
          return i.hasNext();
        }

        @Override
        public char nextChar() {
          // the only difference with CharIterator is unboxing
          return i.next().getKey();
        }

        @Override
        public void remove() {
          i.remove();
        }
      };
    }
  }

  /**
   * Accepts a label and returns the outgoing transition corresponding to it.
   * @param label Identifier of the outgoing transition to return
   * @return Outgoing transition corresponding to the label
   */
  public DawgNode transition(final char label) {
    return edges.get(label);
  }

  /**
   * Adds an edge to the outgoing edges of this DAWG node.
   * @param label Identifier of the edge
   * @param target Neighbor receiving the directed edge
   * @return A DAWG node having the new edge
   */
  public DawgNode addEdge(final char label, final DawgNode target) {
    edges.put(label, target);
    return this;
  }

  /**
   * Removes all outoing-edges.
   */
  public void clear() {
    edges.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object object) {
    if (null == object) {
      return false;
    }

    if (this == object) {
      return true;
    }

    if (!(object instanceof DawgNode)) {
      return false;
    }

    final DawgNode other = (DawgNode) object;

    return Objects.equals(edges, other.edges) && isFinal() == other.isFinal();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(edges.hashCode(), isFinal());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "edges: " + edges.toString() + "; isFinal: " + isFinal();
  }
}
