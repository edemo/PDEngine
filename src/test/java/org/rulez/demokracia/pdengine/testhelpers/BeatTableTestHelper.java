package org.rulez.demokracia.pdengine.testhelpers;

import java.util.List;
import java.util.Set;

import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.beattable.Pair;

public class BeatTableTestHelper {

  public static final String CHOICE1 = "name1";
  public static final String CHOICE2 = "name2";
  public static final String CHOICE3 = "name3";

  public static final Pair PAIR = new Pair(4, 5);

  public static BeatTable createNewBeatTableWithData() {
    final BeatTable beatTable = initBeatTable();
    beatTable.setElement(CHOICE1, CHOICE2, PAIR);
    return beatTable;
  }

  private static BeatTable initBeatTable() {
    final List<String> list = List.of(CHOICE1, CHOICE2, CHOICE3);
    return new BeatTable(list);
  }

  public static BeatTable createNewBeatTableWithComplexData() {
    final BeatTable beatTable = initBeatTable();
    beatTable.setElement(CHOICE1, CHOICE2, new Pair(5, 1));
    beatTable.setElement(CHOICE1, CHOICE3, new Pair(4, 2));
    beatTable.setElement(CHOICE2, CHOICE1, new Pair(1, 5));
    beatTable.setElement(CHOICE2, CHOICE3, new Pair(11, 4));
    beatTable.setElement(CHOICE3, CHOICE1, new Pair(2, 4));
    beatTable.setElement(CHOICE3, CHOICE2, new Pair(4, 11));
    beatTable.setElement(CHOICE1, CHOICE1, new Pair(0, 0));
    beatTable.setElement(CHOICE2, CHOICE2, new Pair(0, 0));
    beatTable.setElement(CHOICE3, CHOICE3, new Pair(0, 0));
    return beatTable;
  }

  public static BeatTable createNewBeatTableWithEqualData() {
    final BeatTable beatTable = createNewBeatTableWithData();
    beatTable.setElement(CHOICE1, CHOICE2, new Pair(2, 2));
    beatTable.setElement(CHOICE2, CHOICE1, new Pair(2, 2));
    beatTable.setElement(CHOICE1, CHOICE3, new Pair(4, 1));
    beatTable.setElement(CHOICE3, CHOICE1, new Pair(4, 4));
    beatTable.setElement(CHOICE2, CHOICE3, new Pair(4, 1));
    beatTable.setElement(CHOICE3, CHOICE2, new Pair(4, 4));
    return beatTable;
  }

  public static BeatTable createTransitiveClosedBeatTable() {
    return createTransitiveClosedBeatTable(List.of("A", "B", "C", "D"));
  }

  public static BeatTable
      createTransitiveClosedBeatTable(final List<String> keys) {
    final BeatTable beatTable = new BeatTable(keys);
    addPairIfInKeys(beatTable, "A", "C", new Pair(4, 2));
    addPairIfInKeys(beatTable, "A", "D", new Pair(3, 1));
    addPairIfInKeys(beatTable, "B", "C", new Pair(4, 2));
    addPairIfInKeys(beatTable, "B", "D", new Pair(3, 1));
    addPairIfInKeys(beatTable, "C", "D", new Pair(2, 0));
    return beatTable;
  }

  private static void addPairIfInKeys(
      final BeatTable beatTable, final String winner, final String loser,
      final Pair pair
  ) {
    if (beatTable.getKeyCollection().containsAll(Set.of(winner, loser)))
      beatTable.setElement(winner, loser, pair);
  }
}
