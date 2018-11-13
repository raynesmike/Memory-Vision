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
		this.checkType(val.trim());
		
	}
	public Variable() {
		this.variable = "";
		this.value = "";
		this.address = 1000;	
		
	}
	
	public void checkType(String val) {
		
		if (valueType == 1 && pointerType == 0) {
			this.intType(val);
		}
		if (valueType == 2 && pointerType == 0) {
			this.charType(val);
		}
		if (valueType == 3 && pointerType == 0) {
			this.floatType(val);
		}
		if (valueType == 4 && pointerType == 0) {
			this.doubleType(val);
		}
		
		//this.value = val;
	}
	
	public void intType(String val) {
		//-2,147,483,648 to 2,147,483,647 
		
		for (int i=0; i< val.length() ; i++) {
			if(!Character.isDigit(val.charAt(i))) {
				this.value = "Invalid Type";
			}
		}
		try{
			Integer.parseInt(val);
			this.value = val;
		}catch(NumberFormatException e){
			e.printStackTrace();
			this.value = "Invalid Type";
		}
		
	}
	public void charType(String val) {
		if(val.length() > 1) {
			this.value = "Invalid Type";
		}else {
			value = val;
		}
		
	}
	public void floatType(String val) {
		for (int i=0; i< val.length() ; i++) {
			if(!Character.isDigit(val.charAt(i))) {
				this.value = "Invalid Type";
			}
		}
		try{
			Float.parseFloat(val);
			this.value = val;
		}catch(NumberFormatException e){
			e.printStackTrace();
			this.value = "Invalid Type";
		}
	}
	public void doubleType(String val) {
		for (int i=0; i< val.length() ; i++) {
			if(!Character.isDigit(val.charAt(i))) {
				this.value = "Invalid Type";
			}
		}
		try{
			Double.parseDouble(val);
			this.value = val;
		}catch(NumberFormatException e){
			e.printStackTrace();
			this.value = "Invalid Type";
		}
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
