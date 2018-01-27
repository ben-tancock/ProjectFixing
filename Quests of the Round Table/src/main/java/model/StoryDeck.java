package model;

import java.util.ArrayList;
import java.util.Collections;

public class StoryDeck extends ArrayList<Story>{

	/**
	 * added this to scare off warnings and to make it serial as well
	 */
	private static final long serialVersionUID = 1L;
	
	public StoryDeck() {
		
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}

}
