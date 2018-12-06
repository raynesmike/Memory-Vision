/** 
 * Application Programming Project 
 * @author * 
 * -Tyler Mitchell
 * -Jamal Dabas
 * -Michael Raynes
 * UTSA CS 3443 Application Programming
 * Fall 2018 
 **/
package application.model;


public class Variable extends Table{
	private static final Table Table = null;
	private String variable;
	private String value;
	private int address;
	private int pointerType;
	private int valueType;
	private boolean sPointer;
	private int sPointVal;
	private boolean dPointer;
	private int dPointVal;
	/**
	 * Constructor
	 * @param name String
	 * @param address int
	 * @param val int
	 */
	public Variable(String name, int address, String val, int pointer, int type) {
		
		this.variable = name;
		this.pointerType = pointer;
		this.address = address;
		this.valueType = type;
		this.sPointer = false;
		this.dPointer = false;
		
		this.checkType(val.trim());
		if(this.pointerType == 1) {
			this.sPointer = true;
		}
		if(this.pointerType == 2) {
			this.dPointer = true;
		}
	}
	/**
	 * constructor
	 */
	public Variable() {
		this.variable = "";
		this.value = "";
		this.address = 1000;	
		
	}
	
	/**
	 * valueType determines variable type
	 * @param val String
	 */
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
		if (pointerType == 1) {
			this.value = val;
		}
	}
	
	/**
	 * determining of int
	 * @param val String
	 */
	public void intType(String val) {
		for (int i=0; i< val.length() ; i++) {
			if(!Character.isDigit(val.charAt(i))) {
				this.value = "Invalid Type";
			}
		}
		try{
			Integer.parseInt(val);
			this.value = val;
		}catch(NumberFormatException e){
			//e.printStackTrace();
			this.value = "Invalid Type";
		}
		
	}
	/**
	 * determine if char
	 * @param val String
	 */
	public void charType(String val) {
		if(val.length() > 1) {
			this.value = "Invalid Type";
		}else {
			value = val;
		}
		
	}
	/**
	 * determine if float
	 * @param val String
	 */
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
			//e.printStackTrace();
			this.value = "Invalid Type";
		}
	}
	/**
	 * determine if double
	 * @param val String
	 */
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
			//e.printStackTrace();
			this.value = "Invalid Type";
		}
	}
	/**
	 * getter method
	 * @return variable String
	 */
	public String getVariable() {
		return variable;
	}
	/**
	 * setter method
	 * @param variable String
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}
	/**
	 * getter method
	 * @return address int
	 */
	public int getAddress() {
		return address;
	}
	/**
	 * setter method
	 * @param address int
	 */
	public void setAddress(int address) {
		this.address = address;
	}
	/**
	 * getter method
	 * @return value String
	 */
	public String getValue() {	
		return value;
	}
	/**
	 * setter method
	 * @param value String
	 */
	public void setValue(String value) {
		checkType(value);
	}
	/**
	 * getter method
	 * @return pointerType int
	 */
	public int getPointerType() {
		return pointerType;
	}
	/**
	 * setter method
	 * @param pointerType int
	 */
	public void setPointerType(int pointerType) {
		this.pointerType = pointerType;
	}
	/**
	 * getter method
	 * @return valueType int
	 */
	public int getValueType() {
		return valueType;
	}
	/**
	 * setter method
	 * @param valueType int
	 */
	public void setValueType(int valueType) {
		this.valueType = valueType;
	}
	/**
	 * toString()
	 */
	public String toString() {
		return variable +"="+ value + "--"+String.valueOf(address);
	}
}
