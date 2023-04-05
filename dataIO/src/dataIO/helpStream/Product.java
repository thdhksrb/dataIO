package dataIO.helpStream;

import java.io.Serializable;

public class Product implements Serializable
{
	
	private static final long serialVersionUID = 4195297547114120090L;
	private String name;
	private int price;
	
	public Product(String name,int price) {
		this.name = name;
		this.price = price;
	}
	
	
	public String toString() {
		return name + ": " + price;
	}

}
