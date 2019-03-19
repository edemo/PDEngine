package org.rulez.demokracia.pdengine;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;

import com.sun.tools.javac.util.Pair;

public class BeatTable {
	@ElementCollection
	public Map<Choice, HashMap<Choice, Integer>> table;

	public BeatTable() {
		table = new HashMap<Choice, HashMap<Choice, Integer>>();
	}

	public int beatInformation(Choice choice1, Choice choice2, String direction) { //change String direction param to Direction
		//Check 
		if(direction.equals("DIRECTION_FORWARD"))
			return table.get(choice1).get(choice2);
		else if(direction.equals("DIRECTION_BACKWARD"))
			return table.get(choice2).get(choice1);
		//else exception
		return 0;
	}
	
	public int getPair(Choice choice1, Choice choice2) { //Do it with Pair return
		//Check: is it still exist?
		int first = table.get(choice1).get(choice2);
		//Check: is it still exist?
		int second = table.get(choice2).get(choice1);
		//create Pair
		return 0;
	}
	
	public void setPair(Choice choice1, Choice choice2, int i1, int i2) { //do with Pair arg instead of i1,i2
		//Check: is it still exist?
		HashMap<Choice, Integer> key1 = table.get(choice1);
		//Check: is it still exist?
		HashMap<Choice, Integer> key2 = table.get(choice2);
		key1.put(choice2, i1);
		key2.put(choice1, i2);
		
		
	}

}
