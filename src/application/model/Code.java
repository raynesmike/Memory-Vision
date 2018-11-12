package application.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Code {
	private String codes;
	private int startAddress;
	private int curAddress;
	private Table table;
	//public static ArrayList<codes>functions;
	
	/**
	 * Constructor
	 */
	public Code() {
		this.startAddress=1000;
		this.curAddress=1000;
		this.codes = null;
		this.table = new Table();
	}
	
	public ArrayList<String> readCode( String codeString ) {
		this.codes = codeString;
		
		ArrayList<String> wholeCode = new ArrayList<String>();
		Scanner scan = new Scanner(codeString);
		
		while( scan.hasNextLine() ) {
			String line = scan.nextLine();
			
			wholeCode.add( line );
			
			this.readPerLine( line );
			//System.out.println( line );
		}
		
		return wholeCode;
	}
	
	public void readPerLine( String line ) {
		String[] word =line.split(" ");
		
		// add the variables being classified.
		switch(word[0]){
			case "int":
				//System.out.print(word[0]);
				table.classifyLine(line);
				break;
			case "char":
				//System.out.println(word[0]);
				//table.classifyLine(line);
				break;
			case "float":
				//System.out.println(word[0]);
				//table.classifyLine(line);
				break;
			case "double":
				//System.out.println(word[0]);
				table.classifyLine(line);
				break;
			default: // actions
			    //this.table.modify(line);
				
			    break;
		}
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public int getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}

	public int getCurAddress() {
		return curAddress;
	}

	public void setCurAddress(int curAddress) {
		this.curAddress = curAddress;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}




}
