package application.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Code {
	private ArrayList<String> codes;
	private Table table;
	//public static ArrayList<codes>functions;
	
	public Code() {
		this.codes = null;
		this.table = new Table();
	}
	
	public ArrayList<String> readCode( String codeString ) {
		ArrayList<String> wholeCode = new ArrayList<String>();
		Scanner scan = new Scanner(codeString);
		
		while( scan.hasNextLine() ) {
			String line = scan.nextLine();
			wholeCode.add( line );
			System.out.println( line );
		}
		this.findType( wholeCode );
		
		return wholeCode;
	}
	
	public void findType( ArrayList<String> wholeCode ) {
		
		for ( String line: wholeCode ) {
			String[] word =line.split(" ");
			
			// add the variables being classified.
			
			switch(word[0]){
				case "int":
					System.out.print(word[0]);
					table.classifyLine(line);
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
				    this.table.modify(line);
					
				    break;
				
			}
		}
	}


	public ArrayList<String> getCodes() {
		return codes;
	}
	
	public void setCodes(ArrayList<String> codes) {
		this.codes = codes;
	}	
	

}
