package application.model;

import java.util.ArrayList;

public class Variable {
	private String variable;
	private int address;
	private String value;
	
	/**
	 * Constructor
	 * @param name
	 * @param address
	 * @param val
	 */
	public Variable(String name, int address, String val) {
		this.variable = name;
		this.value = val;
		this.address = address;
	}
	
	/**
	 * Setters and Getters
	 * @return
	 */
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
