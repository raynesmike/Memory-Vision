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
	
	public void classifyLine(String s) {
		newVar = new Variable();
		String[] words =s.split(" ");
		int pointer = 0;
		int lenOfPType = words[0].length();
		for(int i= lenOfPType+1; i < s.length(); i++) {
			//System.out.print(s.charAt(i));
			/**
			 * stop when end or after the variable name;
			 */
			if(s.charAt(i)=='=' || s.charAt(i) == ';') {
				break;
			}
			if(s.charAt(i)==('*')) {
				pointer++;
			}
		}
		lenOfPType +=pointer;
		//System.out.printf("\t %d\n", pointer);
		Variable resultVar;
		switch(pointer) {
			case 0:
				//System.out.println("Integer");
				resultVar = newVar.assign(s, lenOfPType, pointer);
				table.add(resultVar);
				break;
			case 1:
				//System.out.println("Pointer");
				resultVar = newVar.assign(s, lenOfPType, pointer);
				table.add(resultVar);
				break;
			case 2:
				//System.out.println("Double Pointer");
				resultVar = newVar.assign(s, lenOfPType, pointer);
				table.add(resultVar);
				break;
			default:
				System.out.printf("ERROR");
				break;
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
