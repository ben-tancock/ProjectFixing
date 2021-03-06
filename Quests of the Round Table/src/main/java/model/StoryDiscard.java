package model;

import java.util.ArrayList;

public class StoryDiscard extends ArrayList<Story>{

	/**
	 * added this to scare off warnings and to make it serial as well
	 */
	private static final long serialVersionUID = 1L;
	
	public String toString() {
		String s = "";
		for(Story st : this) {
			s += st.getName() + ",";
		}
		return s;
	}

}
