package application.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Code {
	private ArrayList<String> codes;
	//public static ArrayList<codes>functions;
	public Code() {
		
	}
	
	public ArrayList<String> readCode(String codeString) {
		ArrayList<String> tokens = new ArrayList<String>();
		Scanner scan = new Scanner(codeString);
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			tokens.add(line);
			System.out.println(line);
		}
		this.findType(tokens);
		return tokens;
	}
	
	public void findType(ArrayList<String> tokens) {
		for (String s: tokens) {
			String[] words =s.split(" ");
			switch(words[0]){
				case "int":
					System.out.print(words[0]);
					Integer.classify(s);
					break;
				case "char":
					System.out.println(words[0]);
					break;
				case "float":
					System.out.println(words[0]);
					break;
				case "double":
					System.out.println(words[0]);
					break;
				default: // actions
				    this.modify(s);
					
				    break;
				
			}
		}
	}
	public void modify(String s) {
		System.out.println(s);
	}

	public ArrayList<String> getCodes() {
		return codes;
	}
	
	public void setCodes(ArrayList<String> codes) {
		this.codes = codes;
	}	
	

}
