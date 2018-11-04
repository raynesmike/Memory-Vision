package application.model;

public class Variable {
	private String[] var;
	private int pointer;
	private Address address;
	private Value value;
	
	public Variable(String[] words, int pointer) {
		this.var = words;
		this.pointer = pointer;
		this.address = null;
		this.value = null;
	}
	

}
