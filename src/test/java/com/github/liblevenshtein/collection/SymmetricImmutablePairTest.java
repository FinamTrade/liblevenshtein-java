package com.github.liblevenshtein.collection;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class SymmetricImmutablePairTest {

  private static final String A = "a";

  private static final String B = "b";

  private static final String C = "c";

  @DataProvider(name = "equivalentPairs")
  public Object[][] equivalentPairs() {
    return new Object[][] {
      {build(A, A), build(A, A)},
      {build(A, B), build(B, A)},
      {build(B, A), build(A, B)},
    };
  }

  @DataProvider(name = "inequivalentPairs")
  public Object[][] inequivalentPairs() {
    return new Object[][] {
      {build(A, B), build(A, C)},
    };
  }

  @Test(dataProvider = "equivalentPairs")
  public void testEquivalentPairs(
      final SymmetricImmutablePair<String> lhs,
      final SymmetricImmutablePair<String> rhs) {

    assertThat(lhs).isEqualByComparingTo(lhs);
    assertThat(rhs).isEqualByComparingTo(rhs);
    assertThat(lhs).isEqualByComparingTo(rhs);
    assertThat(rhs).isEqualByComparingTo(lhs);

    assertThat(lhs).isEqualTo(lhs);
    assertThat(rhs).isEqualTo(rhs);
    assertThat(lhs).isEqualTo(rhs);
    assertThat(rhs).isEqualTo(lhs);

    assertThat(lhs.hashCode()).isEqualTo(rhs.hashCode());

    Map<SymmetricImmutablePair<String>,Integer> map;

    map = new HashMap<>(2);

    map.put(lhs, 1);
    assertThat(map).containsEntry(lhs, 1);
    assertThat(map).containsEntry(rhs, 1);

    map.put(rhs, 2);
    assertThat(map).containsEntry(rhs, 2);
    assertThat(map).containsEntry(lhs, 2);

    map = new TreeMap<>();

    map.put(lhs, 1);
    assertThat(map).containsEntry(lhs, 1);
    assertThat(map).containsEntry(rhs, 1);

    map.put(rhs, 2);
    assertThat(map).containsEntry(rhs, 2);
    assertThat(map).containsEntry(lhs, 2);
  }

  @Test(dataProvider = "inequivalentPairs")
  public void testInequivalentPairs(
      final SymmetricImmutablePair<String> lhs,
      final SymmetricImmutablePair<String> rhs) {

    assertThat(lhs).isEqualByComparingTo(lhs);
    assertThat(rhs).isEqualByComparingTo(rhs);
    assertThat(lhs).isLessThan(rhs);
    assertThat(rhs).isGreaterThan(lhs);

    assertThat(lhs).isEqualTo(lhs);
    assertThat(rhs).isEqualTo(rhs);
    assertThat(lhs).isNotEqualTo(rhs);
    assertThat(rhs).isNotEqualTo(lhs);

    assertThat(lhs.hashCode()).isNotEqualTo(rhs.hashCode());
  }

  public SymmetricImmutablePair<String> build(
      final String first,
      final String second) {
    return new SymmetricImmutablePair<>(first, second);
  }
}
