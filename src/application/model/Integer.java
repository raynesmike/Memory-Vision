package application.model;

public class Integer {

	private int size;
	private String name;
	private String value;
	private Variable var;
	
	public Integer(int size, String name, String value) {
		this.size = 4;
		this.name = name;
		this.value = value;
	}
	

	/**
	 * Getters and Setters
	 * @return
	 */
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
