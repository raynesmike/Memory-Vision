package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PopulateTable {
    
    private final SimpleStringProperty name;
   
    public PopulateTable(String name) {
        super();
        
        this.name = new SimpleStringProperty(name);
       
    }
    public String getName() {
        return name.get();
    }
    
}