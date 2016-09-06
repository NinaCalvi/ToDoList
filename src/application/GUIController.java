package application;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.scene.control.ListCell;
import javafx.util.Callback;
import javafx.util.Duration;
import application.db.database;

public class GUIController implements Initializable {
	@FXML
	Button addButton;
	
	@FXML
	TextField descriptionText;
	
	@FXML
	ListView<String> eventList;
	
	ObservableList<String> list = FXCollections.observableArrayList();
	
	static ListView<String> copy_eventList;
	HashMap<Integer, String> tuple;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		tuple = Main.getDatabase().getData();
		
		System.out.println("initializing");
		if(tuple.isEmpty()){
	
		}else{
			for(int i : tuple.keySet()){
				String todo = tuple.get(i);
				list.add(i, todo);
			}
			makeCell();
			eventList.setItems(list);
			
		}	
		
	}
	
	@FXML
	//event == press the button
	public void buttonPressed(Event e){
		
		//checking if textfield is empty - if so, don't add in to do list
		if(descriptionText.getText().isEmpty() || descriptionText.getText().trim().length() == 0){
			return;
		}
		
		//adding each new toDo at the top of the list (index 0)
		list.add(0, descriptionText.getText());
		Main.getDatabase().update(descriptionText.getText());
		System.out.println("List length: " + list.size());
			
		makeCell();
			
		//prints out the items
		eventList.setItems(list);
		//set text field empty
		descriptionText.deleteText(0, descriptionText.getLength());
		descriptionText.setPromptText("What I can not forget");
		
		
		
	}
	
	
	//pop up window
	public void setPopUp(CellList cell){
		System.out.println("popping up the window");
		
		//Use the CONTROLFX library
		PopOver popup = new PopOver();
		popup.setDetachable(false);
		popup.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
		
		VBox dialog = new VBox(10);
		dialog.setId("popup_vbox");
		HBox thing = new HBox(10);
		thing.setId("popup_hbox");
		Button deleteBtn = new Button("Yes");
		Button cancelBtn = new Button("Cancel");
		Region region = new Region();
		Text popupText = new Text("Would you like to delete?");
		popupText.setId("popup_text");
		dialog.getChildren().add(popupText);
		
		thing.getChildren().add(deleteBtn);
		thing.getChildren().add(region);
		HBox.setHgrow(region, Priority.ALWAYS);
		thing.getChildren().add(cancelBtn);
		
		dialog.getChildren().add(thing);
		
		
		
		popup.setContentNode(dialog);
		
		//set pop up with reference to the cell
		
		popup.show(cell);
		Stage ownerWindow = (Stage) cell.getScene().getWindow();
		popup.show(ownerWindow);
		
		
		cancelBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				popup.hide();
				
			}
			
		});
		
	
		deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				int i = cell.getIndex();
				list.remove(i);
				
				eventList.refresh();
			
				Main.getDatabase().delete(cell.getIndex());
				popup.hide();
			}
			
		});
		
		//BUG FIX - when the main window is closed, if a popup window is open
		//close the pop up as well - otherwise it would give an error
		ownerWindow.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent e){
				//saving the database
				Main.stageIsClosing(ownerWindow);
				//means close immediately with ownerWindow - not after. 
				popup.hide(Duration.millis(0));
				
			
			}
		});
		
		
		
	}
	
	public void makeCell(){
		//modifies the aesthetic of the cell in listview
		eventList.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){
			@Override
			public ListCell<String> call(ListView<String> l_list){
				//calls CellList class - defined the look
				
				CellList cell = new CellList(descriptionText.getText());
				
				//specifies what happens when the specific cell has been clicked	
				cell.setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent e){
						//identifies which cell has been pressed on
						System.out.println("cell clicked:" + cell.getIndex());			
						//ensures pop up doesn't happen by clicking on empty cells
						if(cell.getIndex() < list.size()){
							//makes sure that the specific cell is underlined
							eventList.getSelectionModel().clearAndSelect(cell.getIndex());
							setPopUp(cell);
						}
							
					}
					
				
				});	
				
				
				return cell;
			}
					
					
					
		});
	
	}
	
	
}
	


