package application.model;

public class Variable {
	private String variable;
	private String value;
	private int address;
	
	public Variable(String var, String val, int address) {
		this.variable = var;
		this.value = val;
		this.address = address;
	}
	
}
