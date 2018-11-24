package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.net.URISyntaxException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
 


public class ImageSlideController implements Initializable {
	
	@FXML
	private VBox image_container;
	
	@FXML
	private ImageView slider_image;
	
	@FXML
	private FontAwesomeIcon indicator1, indicator2, indicator3, forward, backward;
	
	int image_index = 0;
	String images[] = new String[] {"img1.png", "img2.png", "img3.png"};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		try {
			String img1Path = getClass().getResource("../view/img1.png").toURI().toString();
			Image img1 = new Image(img1Path);
			slider_image.setImage(img1);
			setCircleIcon(indicator1);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	void nextImage() {
		image_index++;
		if(image_index == images.length) {
			image_index = 0;
			
		}
		switch (image_index) {
		case 0:
			setCircleIcon(indicator1);
			resetCircleIcon(indicator2);
			resetCircleIcon(indicator3);
			break;
		case 1:
			setCircleIcon(indicator2);
			resetCircleIcon(indicator1);
			resetCircleIcon(indicator3);
			break;
		case 2:
			setCircleIcon(indicator3);
			resetCircleIcon(indicator1);
			resetCircleIcon(indicator2);
			break;
		default:
			break;
		}
		
	
		
		
		
		try {
			String imagePath = getClass().getResource("../view/" + images[image_index]).toURI().toString();
			Image image = new Image(imagePath);
			slider_image.setImage(image);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@FXML
	void prevImage() {
		
		
		if(image_index == 0) {
			image_index = images.length;
			
		} 
		image_index--;
		switch (image_index) {
		case 0:
			setCircleIcon(indicator1);
			resetCircleIcon(indicator2);
			resetCircleIcon(indicator3);
			break;
		case 1:
			setCircleIcon(indicator2);
			resetCircleIcon(indicator1);
			resetCircleIcon(indicator3);
			break;
		case 2:
			setCircleIcon(indicator3);
			resetCircleIcon(indicator1);
			resetCircleIcon(indicator2);
			break;
		default:
			break;
		}
		
	
		
		
		
		try {
			String imagePath = getClass().getResource("../view/" + images[image_index]).toURI().toString();
			Image image = new Image(imagePath);
			slider_image.setImage(image);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	

	void setCircleIcon(FontAwesomeIcon indicator) {
		indicator.setIconName("CIRCLE");
	}
	
	void resetCircleIcon(FontAwesomeIcon indicator) {
		indicator.setIconName("CIRCLE_ALT");;
	}

}
