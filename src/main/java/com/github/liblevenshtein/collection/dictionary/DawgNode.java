package com.github.liblevenshtein.collection.dictionary;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.chars.CharIterator;

import java.io.Serializable;
import java.util.Objects;

/**
 * Non-final element of a DAWG structure (Directed Acyclic Word Graph).
 * Currently, this is tightly-coupled with character-node types.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class DawgNode implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Outgoing edges of this node.
   */
  protected final Char2ObjectMap<DawgNode> edges;

  /**
   * Constructs a non-final {@link DawgNode}.
   */
  public DawgNode() {
    this(new Char2ObjectRBTreeMap<>());
  }

  public DawgNode(Char2ObjectMap<DawgNode> edges) {
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
    return edges.keySet().iterator();
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
