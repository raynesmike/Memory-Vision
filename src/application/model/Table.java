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
	
	public int classifyLine(String s, int curAdd) {
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
			if(s.charAt(i)!='*' && flagPassEq==0) { varName+=s.charAt(i);} // read the varName
			//TODO get the name and TODO get the elements as a string after { up to }
			
			//TODO can create another class to assign if Array and if not then just call it.
			
			//if(s.charAt(i)=='=' || s.charAt(i) == ';') { break; } //stops before it assign or at end
		}
		//System.out.println(varName+"===" + value);
		lenOfPType +=pointer;
		//if Array then send to Array Class v[0],v[1],v[2] to add this table
		
		if(flagArray==1) {
			curAdd=this.assignArray(varName, curAdd, value);
		
		}else {	
			Variable new1 = new Variable(varName, curAdd, value);
			curAdd+=4;
			table.add(new1);
			//System.out.println(jammy.toString());
		}
		return curAdd;
		
	}
	public int assignArray(String name,int curAdd, String val) {
		
		String varName ="";
		String tokens[] = val.split(",");
		
		for(int i = 0; i <tokens.length; i++) {
			
			varName = name + "[" +String.valueOf(i) + "]";
			Variable new1 = new Variable(varName, curAdd, tokens[i]);
			curAdd +=4;
			table.add(new1);
			
			//System.out.println(jammy.toString());
		}
		return curAdd;
	}
		//System.out.printf("\t %d\n", pointer);
//		Variable resultVar;
//		switch(pointer) {
//			case 0:
//				//System.out.println("Integer");
//				resultVar = newVar.assign(s, lenOfPType, pointer);
//				table.add(resultVar);
//				break;
//			case 1:
//				//System.out.println("Pointer");
//				resultVar = newVar.assign(s, lenOfPType, pointer);
//				table.add(resultVar);
//				break;
//			case 2:
//				//System.out.println("Double Pointer");
//				resultVar = newVar.assign(s, lenOfPType, pointer);
//				table.add(resultVar);
//				break;
//			default:
//				System.out.printf("ERROR");
//				break;
//		}	
//		
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
