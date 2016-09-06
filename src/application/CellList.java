package application;


import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.ListCell;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class CellList extends ListCell<String> {

	private String toDo;
	private Button actionBtn;
	private Text name;
	private HBox pane;
	private Region region;
	
	public CellList(String toDo){
		super();
		this.toDo = toDo;	
		actionBtn = new Button("Done");
 
        name = new Text(toDo);
        pane = new HBox(10);
        region = new Region();
        
        pane.getChildren().add(name);
        pane.getChildren().add(region);
        HBox.setHgrow(region, Priority.ALWAYS);
        pane.getChildren().add(actionBtn);
        actionBtn.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(name, Priority.ALWAYS);
       
      //Lambda expression - when pressed the event specified will be set on action
        actionBtn.setOnAction((event) -> {
             System.out.println("pressed");
             if(name.isStrikethrough()){
            	 name.setStrikethrough(false);
            	 Main.getDatabase().update(0, this.getIndex());
             } else {
            	 name.setStrikethrough(true);
            	 Main.getDatabase().update(1, this.getIndex());
             }
         });
       
	}
	
	
	@Override
	public void updateItem(String item, boolean empty){
		super.updateItem(item, empty);
		//Cell is not editable
		setEditable(false);
		if(item != null){
			//for CSS rendering
			this.setId("cell");
			
			name.setText(item);
			if(Main.getDatabase().isDone(item)){
				System.out.println("DONE!");
				name.setStrikethrough(true);
			}
			setGraphic(pane);
		}else{
			setGraphic(null);
		}
	}
	
	public String getToDo() {
		return toDo;
	}
	
	public void jabWord(){
		name.setStrikethrough(true);
	}
	
	
}
