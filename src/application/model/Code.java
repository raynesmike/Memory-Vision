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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates Code object
 * @author Algorado
 * 
 */

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
	
	/**
	 * reads strings, parse them, and store them in an arraylist, line per line
	 * @param codeString String
	 * @return wholeCode ArrayList<String>
	 */
	public ArrayList<String> readCode( String codeString ) {
		this.codes = codeString;
		
		ArrayList<String> wholeCode = new ArrayList<String>();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(codeString);
		
		while( scan.hasNextLine() ) {
			String line = scan.nextLine();
			
			wholeCode.add( line );
			
			this.readPerLine( line );
			//System.out.println( line );
		}
		
		return wholeCode;
	}
	
	/**
	 * Reading the code and defines types
	 * @param line String
	 */
	public void readPerLine( String line ) {
		String[] word =line.split(" ");
		int addSize = 0;
		int curAdd;
		int prevAdd = curAddress;
		// add the variables being classified.
		switch(word[0]){

			case "int":
				//System.out.print(word[0]);
				addSize=4;
				curAddress = table.classifyLine(line, curAddress, addSize, 1);
				curAdd = curAddress;
				while ((curAdd - prevAdd) % 4 != 0 ) {
					curAdd ++;
				}
				curAddress=curAdd;

				break;
			case "char":
				//System.out.println(word[0]);
				addSize=1;
				curAddress = table.classifyLine(line, curAddress, addSize, 2);
				curAdd = curAddress;
				while ((curAdd - prevAdd) % 4 != 0 ) {
					curAdd ++;
				}
				curAddress=curAdd;break;
			case "float":
				//System.out.println(word[0]);
				addSize=8; 
				curAddress= table.classifyLine(line, curAddress, addSize, 3);

				curAdd = curAddress;
				while ((curAdd - prevAdd) % 4 != 0 ) {
					curAdd ++;
				}
				curAddress=curAdd;
				break;
			case "double":
				//System.out.println(word[0]);
				addSize=8;
				 
				curAddress = table.classifyLine(line, curAddress, addSize, 4);
				curAdd = curAddress;
				while ((curAdd - prevAdd) % 4 != 0 ) {
					curAdd ++;
				}
				curAddress=curAdd;
				break;
			default: // actions
			    table.modify(line);
				
			    break;

		}
	}
	/**
	 * Save button
	 * @param fileName String
	 * @param userString String
	 * @return true
	 * @throws FileNotFoundException FileNotFoundException
	 */
	public Boolean save(String fileName, String userString) throws FileNotFoundException {
		
		FileOutputStream file = new FileOutputStream(fileName, false);
		PrintWriter outFile = new PrintWriter( file );

		outFile.println(userString);
			
		outFile.close();
		
		return true;
	}
	
	/**
	 * Will load code
	 * @param filename String
	 * @return data ArrayList<String>
	 * @throws IOException IOException
	 */
	public static ArrayList<String> loadCode(String filename) throws IOException {
		//File file = new File(fileName);
		File file = new File(filename);
		Scanner scan = new Scanner(file);
		
		ArrayList<String> data = new ArrayList<String>();
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			data.add(line);
			
		}
		//System.out.println(data);
		scan.close();
		return data;
	}
	
	/**
	 * getter method
	 * @return codes String
	 */
	public String getCodes() {
		return codes;
	}

	/**
	 * setter method
	 * @param codes String
	 */
	public void setCodes(String codes) {
		this.codes = codes;
	}

	/**
	 * getter method
	 * @return startAddress
	 */
	public int getStartAddress() {
		return startAddress;
	}

	/**
	 * setter method
	 * @param startAddress String
	 */
	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}

	/**
	 * getter method
	 * @return curAddress int
	 */
	public int getCurAddress() {
		return curAddress;
	}

	/**
	 * setter method
	 * @param curAddress String
	 */
	public void setCurAddress(int curAddress) {
		this.curAddress = curAddress;
	}

	/**
	 * getter method
	 * @return table Table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * setter method
	 * @param table Tavle
	 */
	public void setTable(Table table) {
		this.table = table;
	}
}
