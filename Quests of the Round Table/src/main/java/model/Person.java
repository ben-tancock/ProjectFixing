package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Person.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends Player implements Serializable { 
	
	/**
	 * Testing Serialization
	 */
	private static final long serialVersionUID = -6202423382353287282L;
	public boolean person;
	public Person() {
		
	}
	
	
		
}