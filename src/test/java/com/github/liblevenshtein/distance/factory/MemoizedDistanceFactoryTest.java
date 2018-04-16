package com.github.liblevenshtein.distance.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.liblevenshtein.distance.IDistance;
import com.github.liblevenshtein.transducer.Algorithm;

import static com.github.liblevenshtein.assertion.DistanceAssertions.assertThat;

public class MemoizedDistanceFactoryTest {

  private static final String FOO = "foo";

  private static final String FOOD = "food";

  private static final String FODO = "fodo";

  private static final String FDOO = "fdoo";

  private static final String DFOO = "dfoo";

  private static final String OO = "oo";

  private static final String FO = "fo";

  private static final String BOO = "boo";

  private static final String FBO = "fbo";

  private static final String FOB = "fob";

  private static final String OFO = "ofo";

  private static final String CLOG = "clog";

  private static final String DOG = "dog";

  private List<String> terms;

  private IDistanceFactory<String> factory;

  @BeforeClass
  public void setUp() throws IOException {
    BufferedReader reader = null;
    try {

      reader = new BufferedReader(
              new InputStreamReader(
                      getClass().getResourceAsStream("/top-20-most-common-english-words.txt"),
                      StandardCharsets.UTF_8));
      final List<String> termsList = new ArrayList<String>();

      String term;
      while ((term = reader.readLine()) != null) {
        termsList.add(term);
      }

      this.terms = termsList;
      this.factory = new MemoizedDistanceFactory();
    } catch (Throwable t) {
      if (reader != null) {
        reader.close();
      }
    }
  }

  @DataProvider(name = "equalSelfSimilarityData")
  public Iterator<Object[]> equalSelfSimilarityData() {
    return new EqualSelfSimilarityDataIterator(factory, terms);
  }

  @DataProvider(name = "minimalityData")
  public Iterator<Object[]> minimalityData() {
    return new MinimalityDataIterator(factory, terms);
  }

  @DataProvider(name = "symmetryData")
  public Iterator<Object[]> symmetryData() {
    return new SymmetryDataIterator(factory, terms);
  }

  @DataProvider(name = "triangleInequalityData")
  public Iterator<Object[]> triangleInequalityData() {
    return new TriangleInequalityDataIterator(factory, terms);
  }

  @DataProvider(name = "penaltyData")
  public Object[][] penaltyData() {
    return new Object[][] {
      {Algorithm.STANDARD, factory.build(Algorithm.STANDARD), 2, 2, 2},
      {Algorithm.TRANSPOSITION, factory.build(Algorithm.TRANSPOSITION), 1, 2, 2},
      {Algorithm.MERGE_AND_SPLIT, factory.build(Algorithm.MERGE_AND_SPLIT), 2, 1, 1},
    };
  }

  @Test(dataProvider = "equalSelfSimilarityData")
  public void testEqualSelfSimilarity(
      final Algorithm algorithm,
      final IDistance<String> distance,
      final String term1,
      final String term2) {
    assertThat(distance).satisfiesEqualSelfSimilarity(term1, term2);
  }

  @Test(dataProvider = "minimalityData")
  public void testSatisfyMinimality(
      final Algorithm algorithm,
      final IDistance<String> distance,
      final String term1,
      final String term2) {
    assertThat(distance).satisfiesMinimality(term1, term2);
  }

  @Test(dataProvider = "symmetryData")
  public void testSymmetry(
      final Algorithm algorithm,
      final IDistance<String> distance,
      final String term1,
      final String term2) {
    assertThat(distance).satisfiesSymmetry(term1, term2);
  }

  @Test(dataProvider = "triangleInequalityData")
  public void testTriangleInequality(
      final Algorithm algorithm,
      final IDistance<String> distance,
      final String term1,
      final String term2,
      final String term3) {
    assertThat(distance).satisfiesTriangleInequality(term1, term2, term3);
  }

  @Test(dataProvider = "penaltyData")
  public void testPenalties(
      final Algorithm algorithm,
      final IDistance<String> distance,
      final int transpositionPenalty,
      final int mergePenalty,
      final int splitPenalty) {
    assertThat(distance)
      .hasDistance(0, FOO, FOO)
      .hasDistance(1, FOO, FOOD)
      .hasDistance(1, FOO, FODO)
      .hasDistance(1, FOO, FDOO)
      .hasDistance(1, FOO, DFOO)
      .hasDistance(1, FOO, OO)
      .hasDistance(1, FOO, FO)
      .hasDistance(1, FOO, BOO)
      .hasDistance(1, FOO, FBO)
      .hasDistance(1, FOO, FOB)
      .hasDistance(transpositionPenalty, FOO, OFO)
      .hasDistance(mergePenalty, CLOG, DOG)
      .hasDistance(splitPenalty, DOG, CLOG);
  }

  private abstract static class AbstractDataIterator implements Iterator<Object[]> {

    protected final Algorithm[] algorithms = Algorithm.values();

    protected Object[] params;

    @Override
    public boolean hasNext() {
      advance();
      return null != params;
    }

    @Override
    public Object[] next() {
      advance();
      final Object[] paramsLocal = this.params;
      this.params = null;
      return paramsLocal;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    public abstract void advance();
  }

  private static class EqualSelfSimilarityDataIterator extends AbstractDataIterator {

    private final IDistanceFactory<String> factory;

    private final List<String> terms;

    private final Object[] buffer = new Object[4];

    private int i = 0;

    private int j = 0;

    private int k = 0;

    EqualSelfSimilarityDataIterator(
        final IDistanceFactory<String> factory,
        final List<String> terms) {
      this.factory = factory;
      this.terms = terms;
      final Algorithm algorithm = algorithms[k++];
      buffer[0] = algorithm;
      buffer[1] = factory.build(algorithm);
    }

    @Override
    public void advance() {
      if (null == params) {
        if (j < terms.size()) {
          buffer[2] = terms.get(i);
          buffer[3] = terms.get(j);
          params = buffer;
          j += 1;
        }
        else if (i + 1 < terms.size()) {
          i += 1;
          j = 0;
          advance();
        }
        else if (k + 1 < algorithms.length) {
          i = 0;
          j = 0;
          k += 1;
          final Algorithm algorithm = algorithms[k];
          buffer[0] = algorithm;
          buffer[1] = factory.build(algorithm);
          advance();
        }
      }
    }
  }

  private static class MinimalityDataIterator extends AbstractDataIterator {

    private final IDistanceFactory<String> factory;

    private final List<String> terms;

    private final Object[] buffer = new Object[4];

    private int i = 0;

    private int j = 0;

    private int k = 0;

    MinimalityDataIterator(
        final IDistanceFactory<String> factory,
        final List<String> terms) {
      this.factory = factory;
      this.terms = terms;
      final Algorithm algorithm = algorithms[k++];
      buffer[0] = algorithm;
      buffer[1] = factory.build(algorithm);
    }

    @Override
    public void advance() {
      if (null == params) {
        if (j == i) {
          j += 1;
          advance();
        }
        else if (j < terms.size()) {
          buffer[2] = terms.get(i);
          buffer[3] = terms.get(j);
          params = buffer;
          j += 1;
        }
        else if (i + 1 < terms.size()) {
          i += 1;
          j = 0;
          advance();
        }
        else if (k + 1 < algorithms.length) {
          i = 0;
          j = 0;
          k += 1;
          final Algorithm algorithm = algorithms[k];
          buffer[0] = algorithm;
          buffer[1] = factory.build(algorithm);
          advance();
        }
      }
    }
  }

  private static class SymmetryDataIterator extends AbstractDataIterator {

    private final IDistanceFactory<String> factory;

    private final List<String> terms;

    private Object[] buffer = new Object[4];

    private int i = 0;

    private int j = 0;

    private int k = 0;

    SymmetryDataIterator(
        final IDistanceFactory<String> factory,
        final List<String> terms) {
      this.factory = factory;
      this.terms = terms;
      final Algorithm algorithm = algorithms[k++];
      buffer[0] = algorithm;
      buffer[1] = factory.build(algorithm);
    }

    @Override
    public void advance() {
      if (null == params) {
        if (j < terms.size()) {
          buffer[2] = terms.get(i);
          buffer[3] = terms.get(j);
          params = buffer;
          j += 1;
        }
        else if (i + 1 < terms.size()) {
          i += 1;
          j = 0;
          advance();
        }
        else if (k + 1 < algorithms.length) {
          i = 0;
          j = 0;
          k += 1;
          final Algorithm algorithm = algorithms[k];
          buffer[0] = algorithm;
          buffer[1] = factory.build(algorithm);
          advance();
        }
      }
    }
  }

  private static class TriangleInequalityDataIterator extends AbstractDataIterator {

    private final IDistanceFactory<String> factory;

    private final List<String> terms;

    private Object[] buffer = new Object[5];

    private int i = 0;

    private int j = 0;

    private int k = 0;

    private int l = 0;

    TriangleInequalityDataIterator(
        final IDistanceFactory<String> factory,
        final List<String> terms) {
      this.factory = factory;
      this.terms = terms;
      final Algorithm algorithm = algorithms[l++];
      buffer[0] = algorithm;
      buffer[1] = factory.build(algorithm);
    }

    @Override
    public void advance() {
      if (null == params) {
        if (j < terms.size()) {
          buffer[2] = terms.get(i);
          buffer[3] = terms.get(j);
          buffer[4] = terms.get(k);
          params = buffer;
          j += 1;
        }
        else if (i + 1 < terms.size()) {
          i += 1;
          j = 0;
          advance();
        }
        else if (k + 1 < terms.size()) {
          i = 0;
          j = 0;
          k += 1;
          advance();
        }
        else if (l + 1 < algorithms.length) {
          i = 0;
          j = 0;
          k = 0;
          l += 1;
          final Algorithm algorithm = algorithms[l];
          buffer[0] = algorithm;
          buffer[1] = factory.build(algorithm);
          advance();
        }
      }
    }
  }
}
