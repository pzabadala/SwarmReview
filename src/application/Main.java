package application;
	

import application.net.swarm.ReviewItem;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	private Scene scene;
	final Label label = new Label();
	ObservableList<ReviewItem> data;
	TextField newSwarmFld;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			runStage(createLoginGrid());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public GridPane createListGrid() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 20));
		
		
		Text scenetitle = new Text("Swarm Ruler");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
		grid.add(scenetitle, 0, 0, 2, 1);

		Button newSwarmBtn = new Button("Add");
		grid.add(newSwarmBtn, 0, 1);
		
		newSwarmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				ActionController.getImsMembers();
				if(newSwarmFld.getText().length() != 0 )
					data.add(new ReviewItem(newSwarmFld.getText()));
			}
			
		});

		newSwarmFld = new TextField();
		grid.add(newSwarmFld, 1, 1);
		
		Label newSwarmListLbl = new Label("Swarms:");
		grid.add(newSwarmListLbl, 0, 3);
				
		ListView<ReviewItem> swarmList = createSwarmList();
		grid.add(swarmList, 1, 3);
		
		Label swarmDetailsLbl = new Label("Details:");
		grid.add(swarmDetailsLbl, 0, 4);
		
		Text swarmDetailsFld = new Text();
		swarmDetailsFld.setText("Details");
		swarmDetailsFld.wrappingWidthProperty().set(300);
		
		grid.add(swarmDetailsFld, 1, 4);
		grid.add(label, 1, 5);
		
		return grid;
	}
	
	public GridPane createLoginGrid() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 20));
		
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		Button loginBtn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(loginBtn);
		
		Label authFailed = new Label("");
		authFailed.setTextFill(Color.web("red"));
		grid.add(authFailed, 0, 4);
		
		
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				authFailed.setText("");
				if(ActionController.login(userTextField.getText() , pwBox.getText())) {
					Button btn = (Button)event.getSource();
					Stage stage = (Stage)btn.getScene().getWindow();
					stage.close();
					runStage(createListGrid());
				} else {
					authFailed.setText("Authorization failed");
				}
			}
		});
		grid.add(hbBtn, 1, 4);
		return grid;
		
	}
	
	private void runStage(GridPane grid) {
		Stage stage = new Stage();
		scene = new Scene(grid, 500, 275);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public  ListView<ReviewItem> createSwarmList() {
		ListView<ReviewItem> list = new ListView<ReviewItem>();
	    data = FXCollections.observableArrayList(
	            new ReviewItem("pierwszy"), new ReviewItem("drugi"));
	    
        list.setCellFactory(new Callback<ListView<ReviewItem>, 
            ListCell<ReviewItem>>() {
                @Override 
                public ListCell<ReviewItem> call(ListView<ReviewItem> list) {
                    return new SwarmItemList();
                }
            }
        );
        
	    list.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<ReviewItem>() {
	                public void changed(ObservableValue<? extends ReviewItem> ov, 
	                		ReviewItem old_val, ReviewItem new_val) {
	                        //label.setText(new_val.author);
	                        //label.setTextFill(Color.web(new_val));
	            }
	        });
	    list.setItems(data);
	    return list;
	}
	
	static class SwarmItemList extends ListCell<ReviewItem> {
			
        @Override
        public void updateItem(ReviewItem item, boolean empty) {
            super.updateItem(item, empty);
            
            Rectangle rect = new Rectangle(20, 20);
            if (item != null) {
               rect.setFill(Color.web("red")); 
               setGraphic(rect);
               //setText(item.url);   
            }
        }
    }
}


