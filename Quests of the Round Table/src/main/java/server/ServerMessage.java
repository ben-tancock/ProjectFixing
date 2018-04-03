package server;

import java.util.Date;

public class ServerMessage {
	private Object message;
	private Date time = new Date();
	
	public ServerMessage() {}
	
	public ServerMessage(Object message)
	{
		this.message = message;
	}
	
	public Object getMessage()
	{
	    return message;
	}
	
	public void setMessage(Object message)
	{
	    this.message = message;
	}
	
	public Date getTime()
	{
	    return time;
	}
	
	public String toString()
	{
		return String.format("{time: %2$-15d | mesg: %1$s}", getMessage(), getTime().getTime());
	}	
}
