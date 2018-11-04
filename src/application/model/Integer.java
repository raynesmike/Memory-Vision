package application.model;

public class Integer {

	private int size;
	private String name;
	private String value;
	
	public Integer(int size, String name, String value) {
		this.size = 4;
		this.name = name;
		this.value = value;
	}
	
	public static void classify(String s) {
		// TODO check if its a pointer or not
		String[] words =s.split(" ");
		int pointer = 0;
		for(int i= words[0].length()+1; i < s.length(); i++) {
			System.out.print(s.charAt(i));
			if(s.charAt(i)==('*')) {
				pointer++;
			}
		}		
		
		System.out.printf("\t %d\n", pointer);
		//TODO
		switch(pointer) {
			case 0:
				System.out.println("Integer");
				break;
			case 1:
				System.out.println("Pointer");
				break;
			case 2:
				System.out.println("Double Pointer");
				break;
			default:
				System.out.printf("ERROR");
				
		}

		// TODO assign into an address /if *ptr  
		
	}
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
