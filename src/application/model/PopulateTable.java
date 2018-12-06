package application.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * populates the table
 * @author Algorado
 *
 */
public class PopulateTable {
    
    private final SimpleStringProperty name;
   
    /**
     * Constructor
     * @param name String
     */
    public PopulateTable(String name) {
        super();
        
        this.name = new SimpleStringProperty(name);
       
    }
    /**
     * getter method
     * @return name.get()
     */
    public String getName() {
        return name.get();
    }
    
}