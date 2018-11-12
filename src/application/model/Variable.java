package application.model;

import java.util.ArrayList;

public class Variable {
	private String variable;
	private String value;
	private int address;
	private ArrayList<String> varArray;
	private ArrayList<String> valArray;
	private ArrayList<Integer> addressArray;
	
	private Variable var;
	private int pointerType;
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
	public Variable() {
		this.variable = "";
		this.value = "";
		this.address = 1000;	
		this.varArray= new ArrayList<String>();
		this.valArray= new ArrayList<String>();
		this.addressArray = new ArrayList<Integer>();
		
		
	}
	
	public Variable assign(String line, int starting, int pointer) {
		pointerType = pointer;
		int flagProperty=0;
		int flagArray=0;
		int flagInArray=0;
		int flagNext=0;
		int arrayIndex=0;
		for(int i= starting+1; i < line.length(); i++) {
			//System.out.print(line.charAt(i));
			if (line.charAt(i)=='=') {
				flagProperty = 1; //activate ASSIGNING
			}
			if(flagProperty == 0) {

				variable +=	line.charAt(i);
				if (line.charAt(i) == '[') {
					flagArray=1; // activate ARRAY
					i+=2;
				}
			}
			// after = means assigning values
			if(flagProperty == 1) {
				
				if(flagArray == 1) {
					if(line.charAt(i) == '{') {flagInArray = 1; i++;} //activate INSIDE
					//if(line.charAt(i) == '}') {flagInArray = 0;} //activate OUTSIDE
					if(flagInArray == 1) {
						if(line.charAt(i) == ',' || line.charAt(i) == '}') {
							flagNext = 1;
							valArray.add(value);
							varArray.add(variable+String.valueOf(arrayIndex)+']');
							//System.out.println(variable+String.valueOf(arrayIndex)+']' + value);
							value="";
							arrayIndex++;
						} //activate NEXT
						
						if(flagNext == 0) {
							value +=line.charAt(i); //reading VALUE
						}
						if(line.charAt(i) != ',') {flagNext = 0;}
					}
				}else {
					value += line.charAt(i);
				}
			}
			
		}
		
		//System.out.println(variable+"\n");
		
		var = null;
		return var;
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
