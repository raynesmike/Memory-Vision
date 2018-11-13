package application.model;

import java.util.ArrayList;

public class Table {

	private ArrayList<Variable> table;
	private String variable;
	private String value;
	private int address;
	private int currAddress;
	private int startAddress;
	private Variable newVar;
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
		System.out.println(s);
	}
	
	public int classifyLine(String s, int curAdd , int addSize, int type) {
		newVar = new Variable();
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
			curAdd=this.assignArray(varName, value, curAdd, addSize, pointer, type);
		
		}else {	
			Variable new1 = new Variable(varName, curAdd, value, pointer, type);
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
