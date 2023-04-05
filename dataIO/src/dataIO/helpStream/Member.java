package dataIO.helpStream;

import java.io.Serializable;

public class Member implements Serializable
{
	
	private static final long serialVersionUID = 2597812491301685202L;
	private String id;
	private String name;
	
	public Member(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return id + ":" + name;
	}

}
