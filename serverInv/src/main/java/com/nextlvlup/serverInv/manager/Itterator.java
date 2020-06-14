package com.nextlvlup.serverInv.manager;

public class Itterator {
	
	private int counter;
	
	public Itterator() {
		this.counter = -1;
	}
	
	public Itterator(int startIndex) {
		this.counter = startIndex-1;
	}
	
	public int get() {
		counter++;
		return counter;
	}

}