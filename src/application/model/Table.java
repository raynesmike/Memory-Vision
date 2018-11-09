package application.model;

import java.util.ArrayList;

public class Table {

	private ArrayList<Variable> table;
	private String variable;
	private String value;
	private int address;
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
	public Table(String variable, String address, String value) {
		this.variable = variable;
		this.value = value;
		this.address = 1000;
	}
	public void modify(String s) {
		System.out.println(s);
	}
	
	public void classifyLine(String s) {
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
		
		String[] words =s.split(" ");
		int pointer = 0;
		for(int i= words[0].length()+1; i < s.length(); i++) {
			System.out.print(s.charAt(i));
			if(s.charAt(i)==('*')) {
				pointer++;
			}
		}
		
		/**
		 * TODO 
		 * Create a Table that will contain variable name, address and its value
		 */
		
		System.out.printf("\t %d\n", pointer);
		
		switch(pointer) {
			case 0:
				System.out.println("Integer");
				//Variable(words, pointer);
				
				break;
			case 1:
				System.out.println("Pointer");
				break;
			case 2:
				System.out.println("Double Pointer");
				break;
			default:
				System.out.printf("ERROR");
				break;
		}
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

}
