package questClient;

import java.io.Serializable;

public class UserConnectionMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -29818992944726611L;
	private String userName;
	
	public UserConnectionMessage(String name) {
		userName = name;
	}
	
	public String getName() {
		return userName;
	}

}
