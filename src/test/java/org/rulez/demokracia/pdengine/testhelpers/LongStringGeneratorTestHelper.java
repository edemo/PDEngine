package org.rulez.demokracia.pdengine.testhelpers;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LongStringGeneratorTestHelper {

  public static String generate(final int length) {
    return IntStream.range(0, length)
        .boxed()
        .map(a -> "w")
        .collect(Collectors.joining());
  }

}
