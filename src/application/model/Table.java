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

import java.util.ArrayList;

public class Table {

	private ArrayList<Variable> variableList;
	private String variable;
	private String value;
	private int address;
	private int currAddress;
	private int startAddress;

	/**
	 * Constructor
	 */
	public Table() {

		this.variableList = new ArrayList<Variable>();
	
	}
	/**
	 * Constructor
	 * @param variable String
	 * @param value String
	 * @param address int
	 */
	public Table(String variable, String value, int address) {
		this.variable = variable;
		this.value = value;
		this.address = address;
	}
	
	/**
	 * For statements that modify a variable
	 * @param s String
	 */
	public void modify(String s) {
		String [] tokens = s.split(" ");
		String variable;
		int valueType =0;
		String value = "";
		String use = "";
		int currentValue;
		double currentValueD;
		float currentValueF;
		int flagCome = 0;
		int flagStopAdding;
		
		if (tokens.length == 1) {
			variable = tokens[0];

			if(variable.contains("++") ) {
				
				variable=variable.replace("++;", "");
				//value = Integer.parseInt(tokens[0])++;
				for (Variable a: variableList) {
					if(variable.equals(a.getVariable())) {
						if(a.getValueType() == 1) {
							currentValue = Integer.parseInt(a.getValue()) + 1;
							a.setValue(String.valueOf(currentValue));
						}
						if(a.getValueType() == 3) {
							currentValueD = Double.parseDouble(a.getValue()) + 1;
							a.setValue(String.valueOf(currentValueD));
						}
						if(a.getValueType() == 4) {
							currentValueF = Float.parseFloat(a.getValue()) + 1;
							a.setValue(String.valueOf(currentValueF));
						}
						
					}
				}
				
				
			}
			
			if(variable.contains("--") ) {
				
				variable=variable.replace("--;", "");
				//value = Integer.parseInt(tokens[0])++;
				for (Variable a: variableList) {
					if(variable.equals(a.getVariable())) {
						if(a.getValueType() == 1) {
							currentValue = Integer.parseInt(a.getValue()) - 1;
							a.setValue(String.valueOf(currentValue));
						}
						if(a.getValueType() == 3) {
							currentValueD = Double.parseDouble(a.getValue()) - 1;
							a.setValue(String.valueOf(currentValueD));
						}
						if(a.getValueType() == 4) {
							currentValueF = Float.parseFloat(a.getValue()) - 1;
							a.setValue(String.valueOf(currentValueF));
						}
						
					}
				}
			}
				
		}
		
		// This will only work if there is a an equal and assigning new values
		if(tokens.length > 1) {
			if ("=".equals(tokens[1])) {
				for (Variable x: variableList) {
					if (tokens[0].equals(x.getVariable())) {
						
						if(x.getValueType() == 1 || x.getValueType() == 3 || x.getValueType() == 4) {
							valueType = x.getValueType();
							for(int i = 2; i < tokens.length; i++) {
								//System.out.println(tokens[i].replace(";", "") + "-----" +x.getVariable());
								for(Variable y: variableList) {
									if(tokens[i].replace(";", "").equals(y.getVariable())) {
										tokens[i] = y.getValue();
									}	
								}
								
								use += tokens[i] + " ";
							}
							use = use.replace(";", "");
							value = this.operation(use,valueType);
							
							x.setValue(value);
							//System.out.println(x.getValue());
						}
						if (x.getValueType() == 2) {
							for(Variable y: variableList) {
								if(tokens[2].replace(";", "").equals(y.getVariable())) {
									if (y.getValueType() == 2) {
										tokens[2] = y.getValue();
										flagCome = 1;
									}
								}	
							
								//char name = 'r';
								//char last = 'z';
								//name = last;
								
								if (flagCome == 1) {
									//flagCome = 1;
									x.setValue(tokens[2]);
								} 
								
								else if(tokens[2].replace(";", "").matches("'.'")){
									value = tokens[2].replaceAll("'", "");
									value = value.replace(";", "");
									System.out.println("THE IS A QUOTE AROUND IT NOW ASSIGN IT");
									x.setValue(value);
								}							
							}
						}
					}		
				}
			}
		}
	}
	
	/**
	 * Used for arithmetic (+ and - only)
	 * @param use String
	 * @param valueType int
	 * @return total2 int
	 */
	public String operation(String use, int valueType){
		System.out.println(use);
		String tokens[] = use.split(" ");
		
		if(tokens.length==1) {
			return use.trim();
		}
		
		String total = "0";
		String arith = "";
		String total2 = "0";
		String total3 = "0";
		int counter = 0;
		int counter1 = 0;
		int counter2 = 0;
		
		for (int i = 0; i < tokens.length; i+=2) {
			//System.out.println(tokens.length-1 + "----" +i);
			System.out.println("-------------");
			//System.out.println(sum+"  +  "+tokens[i]);
			if(i < tokens.length-1) {
				arith = tokens[i+1];
				System.out.println(arith);
			}
			
			total = this.calculate(tokens[i], arith, total, valueType);
			
			if(arith.equals("-") && valueType == 1) {
				if(counter % 2 != 0) {
					total2 = String.valueOf((Integer.parseInt(total) * -1));
				} else {
					total2 = String.valueOf((Integer.parseInt(total2) - Integer.parseInt(tokens[i])));
				}
				counter++;
			} 
			else if(arith.equals("+") && valueType == 1){
				total2 = String.valueOf(Integer.parseInt(total2) + Integer.parseInt(tokens[i]));
			}
			if(arith.equals("-") && valueType == 3) {
				if(counter % 2 != 0) {
					total2 = String.valueOf((Double.parseDouble(total) * -1));
				} else {
					total2 = String.valueOf((Double.parseDouble(total2) - Double.parseDouble(tokens[i])));
				}
				counter1++;
			} 
			else if(arith.equals("+") && valueType == 3){
				total2 = String.valueOf(Double.parseDouble(total2) + Double.parseDouble(tokens[i]));
			}
			if(arith.equals("-") && valueType == 4) {
				if(counter % 2 != 0) {
					total2 = String.valueOf((Float.parseFloat(total) * -1));
				} else {
					total2 = String.valueOf((Float.parseFloat(total2) - Float.parseFloat(tokens[i])));
				}
				counter2++;
			} 
			else if(arith.equals("+") && valueType == 4){
				total2 = String.valueOf(Float.parseFloat(total2) + Float.parseFloat(tokens[i]));
			}
			//System.out.println(sum+"-----@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
		
			return total2;
		
	}
	
	/**
	 * Helps operation in arithmetic
	 * @param one String
	 * @param oper String
	 * @param two String
	 * @param valueType int
	 * @return value String
	 */
	public  String calculate(String one, String oper, String two , int valueType) {
		String value = "";
		String sec = "";
		//System.out.println(one + oper + two);
		switch(oper) {
		case "+":
			if(valueType == 1) {
				value = String.valueOf((Integer.parseInt(one) + Integer.parseInt(two)));
			}
			if(valueType == 3) {
				value = String.valueOf((Double.parseDouble(one) + Double.parseDouble(two)));
			}
			if(valueType == 4) {
				value = String.valueOf((Float.parseFloat(one) + Float.parseFloat(two)));
			}
			break;
		case "-":
			if(valueType == 1) {
				System.out.println("this = " + (Integer.parseInt(one) - Integer.parseInt(two)));
				value = String.valueOf((((Integer.parseInt(one)) - (Integer.parseInt(two)))));
			}
			if(valueType == 3) {
				value = String.valueOf((Double.parseDouble(one) - Double.parseDouble(two)));
			}
			if(valueType == 4) {
				value = String.valueOf((Float.parseFloat(one) - Float.parseFloat(two)));
			}
			break;
			default:
				break;
		}
			return value;
	}
	
	/**
	 * Classifies whether line is initializing or modifying
	 * @param s String
	 * @param curAdd int
	 * @param addSize int
	 * @param type int
	 * @return curAdd int
	 */
	public int classifyLine(String s, int curAdd , int addSize, int type) {
		//newVar = new Variable();
		String[] words =s.split(" ");
		int pointer = 0;
		int lenOfPType = words[0].length();
		int flagArray = 0;
		int flagPassEq = 0;
		int flagAmper = 0;
		int cntElement = 0;
		String varName = "";
		String value = "";
		for(int i= lenOfPType+1; i < s.length(); i++) {
			/**
			 * stop when end or after the variable name;
			 */
			if(flagPassEq==1 && s.charAt(i)!=';' && s.charAt(i) != '{' && s.charAt(i) != '}' 
				&& s.charAt(i) != '\''&&s.charAt(i) != '\"' && s.charAt(i) != '&' ) {
					value+=s.charAt(i);
			}
			if(s.charAt(i)=='=') { flagPassEq = 1;} //this will prevent counting * after '='
			if(s.charAt(i)=='&' && flagPassEq==1) { flagAmper++;}
			if(s.charAt(i)==',') { cntElement++; } // count the element
			if(s.charAt(i)=='[' && flagPassEq==0) { flagArray=1; i+=2;} //classify as Array so that we can assign ea element.
			if(s.charAt(i)=='*' && flagPassEq==0) { pointer++;	} //increase if *ptr or **ptr
			if(s.charAt(i)!='*' && flagPassEq==0 && s.charAt(i)!=';') { varName+=s.charAt(i);} // read the varName
			
		}
		//System.out.println(varName+"===" + value);
		lenOfPType +=pointer;
		//if Array then send to Array Class v[0],v[1],v[2] to add this table
		
		if(flagArray==1) {
			curAdd=this.assignArray(varName.trim(), value, curAdd, addSize, pointer, type);
		
		}else {	
			Variable new1 = new Variable(varName.trim(), curAdd, value, pointer, type);
			//new1.valueType(pointer);
			//if this is a sPointer and assigning
			curAdd+=addSize;
			variableList.add(new1);
			
			if(pointer > 0 && flagPassEq == 1) {
				System.out.println("DOOR");
				pointerAssign(varName.trim(), value.trim(), flagAmper, pointer);
				System.out.println("DONE");
			}
			
			
		}
		return curAdd;
		
	}
	
	/**
	 * Assigns what pointers point to (initialization only)
	 * @param name String
	 * @param value String
	 * @param amper int
	 * @param pointer int
	 */
	public void pointerAssign(String name, String value, int amper, int pointer) {
		System.out.println(value);
		System.out.println("ENTERED");
		for(Variable a: variableList) {
			//TODO: put *p in the arrayList
			System.out.println("CHECK");
			System.out.println(a.getVariable());
			
			if(name.equals(a.getVariable())) {
				System.out.println("FIRST IF");
				for(Variable b: variableList) {
					System.out.println("Other");
					System.out.println(b.getAddress());
					if(value.equals(String.valueOf(b.getVariable()))) {
						a.setValue(String.valueOf(b.getAddress()));
						System.out.println(a.getValue());
					}
				}
			}
		}
	}
	
	/**
	 * method responsible for handling arrays and what they point to
	 * @param name String
	 * @param val String
	 * @param curAdd int
	 * @param addSize int
	 * @param pointer int
	 * @param type int
	 * @return curAdd int
	 */
	public int assignArray(String name, String val, int curAdd, int addSize, int pointer, int type) {
		
		String varName ="";
		String tokens[] = val.split(",");
		
		for(int i = 0; i <tokens.length; i++) {
			
			varName = name + "[" +String.valueOf(i) + "]";
			Variable new1 = new Variable(varName, curAdd, tokens[i], pointer, type);
			curAdd +=addSize;
			variableList.add(new1);
			
			
			//System.out.println(jammy.toString());
		}
		return curAdd;
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
		this.value = value;
	}
	/**
	 * getter method
	 * @return address String
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
	 * @return variableList ArrayList<Variable>
	 */
	public ArrayList<Variable> getVariableList() {
		return variableList;
	}
	/**
	 * setter method
	 * @param table ArrayList<Variable>
	 */
	public void setVariableList(ArrayList<Variable> table) {
		this.variableList = table;
	}
	/**
	 * getter method
	 * @return currAddress int
	 */
	public int getCurrAddress() {
		return currAddress;
	}
	/**
	 * setter method
	 * @param currAddress int
	 */
	public void setCurrAddress(int currAddress) {
		this.currAddress = currAddress;
	}
	/**
	 * getter method
	 * @return startAddress int
	 */
	public int getStartAddress() {
		return startAddress;
	}
	/**
	 * setter method
	 * @param startAddress int
	 */
	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}
	/**
	 * toString
	 */
	public String toString() {
		String tableString = "variable = value -- address\n";
		for(Variable a: variableList) {
			tableString += a.toString() + "\n";
		}
		return tableString;
	}
}
