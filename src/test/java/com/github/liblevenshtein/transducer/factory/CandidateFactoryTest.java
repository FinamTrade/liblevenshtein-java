package com.github.liblevenshtein.transducer.factory;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.transducer.Candidate;

public class CandidateFactoryTest {

  private static final String FOO = "foo";

  @Test
  public void testWithDistance() {
    CandidateFactory.WithDistance candidateFactory = new CandidateFactory.WithDistance();
    final Candidate candidate = candidateFactory.build(FOO, 2);
    assertThat(candidate).isEqualTo(new Candidate(FOO, 2));
  }

  @Test
  public void testWithoutDistance() {
    CandidateFactory.WithoutDistance candidateFactory = new CandidateFactory.WithoutDistance();
    final String candidate = candidateFactory.build(FOO, 2);
    assertThat(candidate).isEqualTo(FOO);
  }
}
