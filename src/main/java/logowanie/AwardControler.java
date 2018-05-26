package logowanie;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.AwardRepository;
import repository.HibernateUtil;
import repository.TaskRepository;
import repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.hibernate.Session;

import data.Award;
//import Application.MainApp;
import data.Task;
import data.User;
 
public class AwardControler extends Application {
    
	
	
	private Label nameAwardLabel, countAwardLabel;
	private TextField nameAwardField, countAwardField;
	private double x=0;
	private double y=0;
	
	private Stage primaryStage;
	
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        primaryStage.setTitle("Panel Nagród");  
  //      primaryStage.initStyle(StageStyle.UNDECORATED);
		this.initStage();
    }
 
    
    private void initStage() {
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
		// prepare grid
		GridPane grid = new GridPane();
	//	grid.setPadding(new Insets(5));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(40, 40, 40, 40));
		
		
	
		
	
		
		Image image = new Image(getClass().getResourceAsStream("/Ikony_i_css/close2.png"));
        Button button1 = new Button();
        button1.getStyleClass().add("close-button");
        button1.setGraphic(new ImageView(image));
        button1.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
        	   Stage stage = (Stage) button1.getScene().getWindow();
        	   stage.close();
            }
        });
        
        Stage stage = new Stage();
        AddAwardView b = new AddAwardView();
        
		
        Button addAwardButton = new Button("DODAJ NAGRODĘ");
        GridPane.setHalignment(addAwardButton, HPos.LEFT);
		addAwardButton.getStyleClass().add("button-click");
		addAwardButton.setOnAction(event -> {
			b.start(stage);
			primaryStage.close();
			});
		addAwardButton.setMinWidth(120);
		addAwardButton.setMinHeight(60);
		grid.add(addAwardButton, 0, 0);
					    	
			
		Button backButton = new Button("WSTECZ");
		backButton.getStyleClass().add("button-click");
		backButton.setOnAction(event -> backToMenu());
		backButton.setMinWidth(180);
		backButton.setMinHeight(60);
		//grid.add(backButton, 1, 2);
		
		
		
		
		EditAwardView a = new EditAwardView();
		Button edit= new Button("EDYTUJ NAGRODĘ");
		GridPane.setHalignment(edit, HPos.LEFT);
		edit.getStyleClass().add("button-click");
		edit.setOnAction(event -> {
		a.start(stage);
		primaryStage.close();
		});
		edit.setMinWidth(60);
		edit.setMinHeight(60);
		grid.add(edit, 0, 1);
		
		
		DeleteAwardView c = new DeleteAwardView();
		Button delete= new Button("USUŃ NAGRODĘ");
		GridPane.setHalignment(delete, HPos.RIGHT);
		delete.getStyleClass().add("button-click");
		delete.setOnAction(event -> {
			c.start(stage);
			primaryStage.close();
			});
		delete.setMinWidth(190);
		delete.setMinHeight(60);
		grid.add(delete, 1, 0);
		
		ChoiceAward h = new ChoiceAward();
		Button choiceA= new Button("WYBRANE NAGRODY");
		GridPane.setHalignment(choiceA, HPos.RIGHT);
		choiceA.getStyleClass().add("button-click");
		choiceA.setOnAction(event -> {
			h.start(stage);
			primaryStage.close();
			});
		choiceA.setMinWidth(60);
		choiceA.setMinHeight(60);
		grid.add(choiceA, 1, 1);

    	AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setBottomAnchor(grid, 120.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0); 
		AnchorPane.setTopAnchor(grid, 80.0);
		AnchorPane.setRightAnchor(button1, 0.0);
		AnchorPane.setTopAnchor(button1, 0.0);
		AnchorPane.setRightAnchor(backButton, 170.0);
		AnchorPane.setTopAnchor(backButton, 280.0);
		anchorPane.getChildren().addAll(backButton,button1, grid);
		
		
	
		Scene scene = new Scene(anchorPane, 500, 380);
		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());
		
//		grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                x = event.getSceneX();
//                y = event.getSceneY();
//            }
//        });
//        
//        //move around here
//        grid.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                primaryStage.setX(event.getScreenX() - x);
//                primaryStage.setY(event.getScreenY() - y);
//            }
//});
		
		
		// Pressing Enter works as pressing button "Sign in"
//		grid.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//			if (event.getCode() == KeyCode.ENTER) {
//				this.addAward();
//			}
//		});

	
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
    
    
    
   
    public void hide() {
		if (this.primaryStage != null) {
			this.primaryStage.hide();
		}
	}
	public void backToMenu()
	{
		primaryStage.close();
	}
}