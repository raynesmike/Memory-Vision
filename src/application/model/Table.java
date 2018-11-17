package application.model;

import java.util.ArrayList;

public class Table {

	private ArrayList<Variable> table;
	private String variable;
	private String value;
	private int address;
	private int currAddress;
	private int startAddress;
	//private Variable newVar;
	// TODO look for the variable name
	
	// TODO assign address
	// { , , , } read, tokenize and assign the addresses to the count of variable
	// will do a contiguous distribution for easier implementation
	
	// TODO assign value

	/**
	 * Constructor
	 */
	public Table() {

		this.table = new ArrayList<Variable>();
	
	}
	public Table(String variable, String value, int address) {
		this.variable = variable;
		this.value = value;
		this.address = address;
	}
	public void modify(String s) {
		String [] tokens = s.split(" ");
		String variable;
		String value = "";
		String use = "";
		int currentValue;
		double currentValueD;
		float currentValueF;
		int flagCome = 0;
		int flagStopAdding;
		
		//int =1;
		//char=2;
		//float=3;
		//double=4;
		
		
		if (tokens.length == 1) {
			variable = tokens[0];

			if(variable.contains("++") ) {
				
				variable=variable.replace("++;", "");
				//value = Integer.parseInt(tokens[0])++;
				for (Variable a: table) {
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
				for (Variable a: table) {
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
				for (Variable x: table) {
					if (tokens[0].equals(x.getVariable())) {
						
						if(x.getValueType() == 1 || x.getValueType() == 3 || x.getValueType() == 4) {
							for(int i = 2; i < tokens.length; i++) {
								//System.out.println(tokens[i].replace(";", "") + "-----" +x.getVariable());
								for(Variable y: table) {
									if(tokens[i].replace(";", "").equals(y.getVariable())) {
										tokens[i] = y.getValue();
									}	
								}
								
								use += tokens[i] + " ";
							}
							use = use.replace(";", "");
							value = this.operation(use);
							
							x.setValue(value);
							//System.out.println(x.getValue());
						}
						if (x.getValueType() == 2) {
							for(Variable y: table) {
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
								//else {
									//x.setValue(y.getValue());
								//}
//								value = tokens[2].replace("'", "");
//								value = value.replaceAll(";", "");
//								x.setValue(value);
//								
							}
						}
					}		
				}
			}
		}
	}
	public String operation(String use){
		
		String tokens[] = use.split(" ");
		String sum = "0";
		
		for (int i = 0; i < tokens.length; i+=2) {
			//System.out.println(tokens.length-1 + "----" +i);
			//System.out.println("----");
			//System.out.println(sum+"  +  "+tokens[i]);
			sum = this.calculate(sum, "+", tokens[i] );
			//System.out.println(sum+"-----@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
		
		
		
		return sum;
	}
	
	public String calculate(String one, String oper, String two ) {
		String value = "";
		//System.out.println(one + oper + two);
		switch(oper) {
		case "+":
			value = String.valueOf((Integer.parseInt(one) + Integer.parseInt(two)));
			//System.out.println(value +"@@@@@@@@@@@@DDDDDDDDDDDDDDDDDIIIIIIIIIIIIIIINNNNNNNNNNNNNNGGGGGGGGGGGGGGGG");
			break;
			default:
				//System.out.println("I FUCKING HATE THIS");
				break;
		}
		
		return value;
	}
	
	
	public int classifyLine(String s, int curAdd , int addSize, int type) {
		//newVar = new Variable();
		String[] words =s.split(" ");
		int pointer = 0;
		int lenOfPType = words[0].length();
		int flagArray = 0;
		int flagPassEq = 0;
		int cntElement = 0;
		String varName="";
		String value="";
		for(int i= lenOfPType+1; i < s.length(); i++) {
			//System.out.print(s.charAt(i));
			/**
			 * stop when end or after the variable name;
			 */
			if(flagPassEq==1 && s.charAt(i)!=';' && s.charAt(i) != '{' && s.charAt(i) != '}' 
				&& s.charAt(i) != '\''&&s.charAt(i) != '\"') {
					value+=s.charAt(i);
			}
			if(s.charAt(i)=='=') { flagPassEq = 1;} //this will prevent counting * after '='
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
			curAdd+=addSize;
			table.add(new1);
		}
		return curAdd;
		
	}
	public int assignArray(String name, String val,int curAdd,int addSize, int pointer, int type) {
		
		String varName ="";
		String tokens[] = val.split(",");
		
		for(int i = 0; i <tokens.length; i++) {
			
			varName = name + "[" +String.valueOf(i) + "]";
			Variable new1 = new Variable(varName, curAdd, tokens[i], pointer, type);
			curAdd +=addSize;
			table.add(new1);
			
			
			//System.out.println(jammy.toString());
		}
		return curAdd;
	}

		/**
		 * TODO 
		 * Create a Table that will contain variable name, address and its value
		 */
		/**
		 *  TODO check if its a pointer or not
		 *	POINTER int *ptr;
 		 *		-- ptr = address of the VARIABLE
		 *		--*ptr = Dereference
		 *	2xPOINTER int **ptr;
		 *		-- dptr = address of the POINTER
		 *		--*dptr = address of the VARIABLE
		 *		--**dptr= VALUE of the VARIABLE
		 */
		//return var;
		// TODO assign into an address /if *ptr  
		

	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public ArrayList<Variable> getTable() {
		return table;
	}
	public void setTable(ArrayList<Variable> table) {
		this.table = table;
	}
	public int getCurrAddress() {
		return currAddress;
	}
	public void setCurrAddress(int currAddress) {
		this.currAddress = currAddress;
	}
	public int getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}

}
