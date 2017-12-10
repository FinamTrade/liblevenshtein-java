package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

import static com.github.liblevenshtein.assertion.DistanceFunctionAssertions.assertThat;

public class SpecialPositionDistanceFunctionTest {

  @Test
  public void testAt() {
    StateFactory stateFactory = new StateFactory();
    PositionFactory positionFactory = new PositionFactory();

    final State state = stateFactory.build(
        positionFactory.build(2, 3, false),
        positionFactory.build(1, 1, false),
        positionFactory.build(4, 2, true));

    final DistanceFunction distance = new DistanceFunction.ForSpecialPositions();
    assertThat(distance).hasDistance(state, 4, 4);
  }
}
