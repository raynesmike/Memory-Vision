package application.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Code {
	private String codes;
	private ArrayList<Table> table;
	//public static ArrayList<codes>functions;
	
	public Code() {
		this.codes = null;
		this.table = new ArrayList<Table>();
	}
	
	public ArrayList<String> readCode( String codeString ) {
		this.codes = codeString;
		
		ArrayList<String> wholeCode = new ArrayList<String>();
		Scanner scan = new Scanner(codeString);
		
		while( scan.hasNextLine() ) {
			String line = scan.nextLine();
			wholeCode.add( line );
			this.readPerLine( line );
			System.out.println( line );
		}
		
		return wholeCode;
	}
	
	public void readPerLine( String line ) {
		String[] word =line.split(" ");
		
		// add the variables being classified.
		switch(word[0]){
			case "int":
				System.out.print(word[0]);
				//table.classifyLine(line);
				break;
			case "char":
				System.out.println(word[0]);
				//table.classifyLine(line);
				break;
			case "float":
				System.out.println(word[0]);
				//table.classifyLine(line);
				break;
			case "double":
				System.out.println(word[0]);
				//table.classifyLine(line);
				break;
			default: // actions
			    //this.table.modify(line);
				
			    break;
			
		
		}
	}


	public String getCodes() {
		return codes;
	}
	
	public void setCodes(String codes){
		this.codes = codes;
	}	
	

}
