package application;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ListViewController{

	public String label;
	public boolean checkedStatus;
	
	@FXML
	Label listLabel;
	
	public ListViewController(String label, boolean checkedStatus ){
		this.label = label;
		this.checkedStatus = checkedStatus;
		setLabel(label);
		
	}
	
	@FXML
	public void setLabel(String label){
		listLabel.setText(label);
	}
	
	
}
