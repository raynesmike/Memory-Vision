package application.model;

import java.util.ArrayList;

public class Variable {
	private String variable;
	private String value;
	private int address;
	private int pointerType;
	private int valueType;
	/**
	 * Constructor
	 * @param name
	 * @param address
	 * @param val
	 */

	public Variable(String name, int address, String val, int pointer, int type) {
		this.variable = name;
		this.pointerType = pointer;
		this.address = address;
		this.valueType = type;
		this.checkType(val);
		
	}
	public Variable() {
		this.variable = "";
		this.value = "";
		this.address = 1000;	
		
	}
	
	public void checkType(String val) {
		
		this.value = val;
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
	
	public String toString() {
		return variable +"="+ value + "->"+String.valueOf(address);
	}
}
