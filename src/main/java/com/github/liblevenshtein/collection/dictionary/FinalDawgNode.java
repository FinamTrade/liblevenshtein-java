package com.github.liblevenshtein.collection.dictionary;

/**
 * Final element of a DAWG structure (Directed Acyclic Word Graph).
 * Currently, this is tightly-coupled with character-node types.
 */
public class FinalDawgNode extends DawgNode {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new {@link FinalDawgNode}, which acts just like a
   * {@link DawgNode} except that {@link #isFinal()} returns true.
   */
  public FinalDawgNode() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFinal() {
    return true;
  }
}
