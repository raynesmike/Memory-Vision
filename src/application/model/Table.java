package application.model;

public class Table {
	
	private String variable;
	private String address;
	private String value;


	/**
	 * Constructor
	 */
	public Table() {
		this.variable = "";
		this.address = "";
		this.value = "";
	}
	public Table(String variable, String address, String value) {
		this.variable = variable;
		this.address = address;
		this.value = value;
	}
	
	/**
	 * Setters and Getters
	 * @return
	 */
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variables) {
		this.variable = variables;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}




}
