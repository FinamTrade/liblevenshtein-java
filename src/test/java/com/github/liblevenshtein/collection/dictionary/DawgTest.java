package com.github.liblevenshtein.collection.dictionary;

import com.github.liblevenshtein.collection.dictionary.factory.DawgFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.liblevenshtein.assertion.SetAssertions.assertThat;

public class DawgTest {

  private List<String> terms;

  private DawgFactory dawgFactory;

  private Dawg emptyDawg;

  private Dawg fullDawg;

  @BeforeClass
  public void setUp() throws IOException {
    BufferedReader reader = null;
    try {

        reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/wordsEn.txt"),
                        StandardCharsets.UTF_8));
      final List<String> termsList = new ArrayList<String>();

      String term;
      while ((term = reader.readLine()) != null) {
        termsList.add(term);
      }

      Collections.sort(termsList);

      this.terms = termsList;
      this.dawgFactory = new DawgFactory();
      this.emptyDawg = dawgFactory.build(new ArrayList<String>(0));
      this.fullDawg = dawgFactory.build(termsList);
    } catch (Throwable t) {
        if (reader != null) {
            reader.close();
        }
    }
  }

  @DataProvider(name = "terms")
  public Iterator<Object[]> terms() {
    return new TermIterator(terms.iterator());
  }

  @Test(dataProvider = "terms")
  public void emptyDawgAcceptsNothing(final String term) {
    assertThat(emptyDawg).doesNotContain(term);
  }

  @Test(dataProvider = "terms")
  public void dawgAcceptsAllItsTerms(final String term) {
    assertThat(fullDawg).contains(term);
  }

  @Test
  public void dawgAcceptsNoTermsItDoesNotContain() {
    assertThat(fullDawg).doesNotContain("", "foobar", "C+");
  }

  @Test
  public void dawgSizeIsSameAsTerms() {
    assertThat(emptyDawg)
      .isEmpty()
      .hasSize(0);
    assertThat(fullDawg)
      .isNotEmpty()
      .hasSize(terms.size());
  }

  @Test
  public void dawgAcceptsEmptyStringIfInTerms() {
    final List<String> termsList = new ArrayList<String>(1);
    termsList.add("");
    final Dawg dawg = dawgFactory.build(termsList);
    assertThat(dawg).contains("");
  }

  @Test
  public void dawgShouldIterateOverAllTerms() {
    final Set<String> termsList = new HashSet<String>(this.terms);
    for (final String term : fullDawg) {
      try {
        assertThat(termsList).contains(term);
      }
      catch (final AssertionError exception) {
        throw new AssertionError("Expected terms to contain: \"" + term + "\"", exception);
      }
      termsList.remove(term);
    }
    if (!termsList.isEmpty()) {
      final String message =
        String.format("Expected all terms to be iterated over, but missed [%s]",
          termsList.stream().collect(Collectors.joining(", ")));
      throw new AssertionError(message);
    }
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void insertingTermsOutOfOrderShouldThrowAnException() {
    final List<String> termsList = new ArrayList<String>(3);
    termsList.add("a");
    termsList.add("c");
    termsList.add("b");
    dawgFactory.build(termsList, true);
  }

  @Test
  public void equivalentDawgsShouldBeEqual() {
    final Dawg other = dawgFactory.build(new ArrayList<String>(terms));
    assertThat(fullDawg).isEqualTo(other);
  }

  private static class TermIterator implements Iterator<Object[]> {

    private final Iterator<String> terms;

    private Object[] params = null;

    private Object[] buffer = new Object[1];

    public TermIterator(Iterator<String> terms) {
      this.terms = terms;
    }

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

    public void advance() {
      if (null == params && terms.hasNext()) {
        buffer[0] = terms.next();
        params = buffer;
      }
    }
  }
}
