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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.hibernate.Session;

import data.Award;
//import Application.MainApp;
import data.Task;
import data.User;
 
public class AddAwardView extends Application {
    
	
	
	private Label nameAwardLabel, countAwardLabel;
	private TextField nameAwardField, countAwardField;
	private double x=250;
	private double y=250;
	private int c=0;
	ArrayList<String> iconList = new ArrayList<String>();
	private Stage primaryStage;
	private Stage primaryStage2;
	Award award=new Award();
	
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        primaryStage.setTitle("Dodaj nagrodę"); 
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
		this.initStage();
    }
  private void addAward() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		AwardRepository taskRepo = new AwardRepository(session);
    	  
		
		award.setName(nameAwardField.getText());
		award.setCount(Integer.parseInt(countAwardField.getText()));
//		award.setPath("ssciezka.haha");
		
		System.out.println(award.toString());
		taskRepo.addAward(award);
		
		session.close();
		primaryStage.close();
		Stage stage = new Stage();
		stage.setTitle("Nagrody");
		AwardControler award = new AwardControler();
		award.start(stage);
	}
    
    private void initStage() {
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
		// prepare grid
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(40, 40, 40, 40));
		
		
		
		nameAwardLabel = new Label("Nazwa nagrody: ");
		nameAwardLabel.getStyleClass().add("label-field");
		grid.add(nameAwardLabel, 0, 1);

		nameAwardField = new TextField();
		nameAwardField.getStyleClass().add("login-field");
		grid.add(nameAwardField, 1, 1);

		countAwardLabel = new Label("Koszt nagrody:");
		countAwardLabel.getStyleClass().add("label-field");
		grid.add(countAwardLabel, 0, 2);

		countAwardField = new TextField();
		countAwardField.getStyleClass().add("login-field");
		grid.add(countAwardField, 1, 2);
		
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
        


		Button addAwardButton = new Button("DODAJ NAGRODĘ");
		addAwardButton.getStyleClass().add("button-click");
		addAwardButton.setOnAction(event -> addAward());

		
	    	AnchorPane anchorPane = new AnchorPane();
			AnchorPane.setBottomAnchor(grid, 120.0);
			AnchorPane.setLeftAnchor(grid, 0.0);
			AnchorPane.setRightAnchor(grid, 0.0); 
			AnchorPane.setTopAnchor(grid, 80.0);
			AnchorPane.setRightAnchor(button1, 0.0);
			AnchorPane.setTopAnchor(button1, 0.0);
			AnchorPane.setRightAnchor(addAwardButton, 170.0);
			AnchorPane.setTopAnchor(addAwardButton, 380.0);
			anchorPane.getChildren().addAll(addAwardButton,button1, grid);
		
		Button backButton = new Button("WSTECZ");
		backButton.getStyleClass().add("button-click");
		backButton.setOnAction(event -> backToMenu());
		GridPane.setHalignment(backButton, HPos.LEFT);
		backButton.setMinWidth(160);
		backButton.setMinHeight(35);
		grid.add(backButton, 0, 6);
		
		Button addIkon= new Button("WYBIERZ IKONE ZADANIA");
		addIkon.getStyleClass().add("button-click");
		addIkon.setOnAction(event -> addIkon());
		addIkon.setMinWidth(160);
		addIkon.setMinHeight(35);
		GridPane.setHalignment(addIkon, HPos.RIGHT);
		grid.add(addIkon, 1, 6);
		
	
		Scene scene2 = new Scene(anchorPane, 500, 500);
		scene2.getStylesheets().add(new File(path + "style.css").toURI().toString());
		
		anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });
        
        //move around here
        anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            }
});
		
		
		// Pressing Enter works as pressing button "Sign in"
		grid.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				this.addAward();
			}
		});

	
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene2);
		primaryStage.show();
    }
    
    
    
    
    public void addIkon()
	{
//    	this.primaryStage2 = primaryStage2;
//        primaryStage2.setTitle("Dodaj nagrodę");  
        //primaryStage.initStyle(StageStyle.UNDECORATED);

		 Stage primaryStage2 = new Stage();
	        primaryStage2.setTitle("Ikony do wyboru");
	        String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
	        

			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setPadding(new Insets(5));
			grid.setHgap(30);
			grid.setVgap(25);
			grid.setPadding(new Insets(40, 40, 40, 40));
			final ImageView imv = new ImageView();
			final Image image2 = new Image(new File(path + "logo_do_logowania2.png").toURI().toString());
			imv.setImage(image2);
			
			final HBox pictureRegion = new HBox();
			pictureRegion.setAlignment(Pos.TOP_CENTER);
			pictureRegion.getChildren().add(imv);
			// formTitle = new Text("Welcome");
		//	grid.add(pictureRegion, 0, 0, 2, 1);
			
			Image image = new Image(getClass().getResourceAsStream("/Ikony_i_css/50.png"));
			Button button1 = new Button();
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/50.png"));
			button1 = new Button();
			button1.getStyleClass().add("award-button");
			button1.setGraphic(new ImageView(image));
			button1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/50.png");
					primaryStage2.close();
				}
			});
			grid.add(button1,0,0);
			Button button2 = new Button();
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/bilety.png"));
			button2 = new Button();
			button2.getStyleClass().add("award-button");
			button2.setGraphic(new ImageView(image));
			button2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/bilety.png");
					primaryStage2.close();
					
				}
			});
			grid.add(button2,0,1);
			Button button3 = new Button();
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/burger.png"));
			button3 = new Button();
			button3.getStyleClass().add("award-button");
			button3.setGraphic(new ImageView(image));
			button3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/burger.png");
					primaryStage2.close();
				}
			});
		
			grid.add(button3,0,2);
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/cyrk.png"));
			Button button4 = new Button();
			button4.getStyleClass().add("award-button");
			button4.setGraphic(new ImageView(image));
			button4.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/cyrk.png");
					primaryStage2.close();
				}
			});
			grid.add(button4,1,0);
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/telefon.png"));
			Button button5 = new Button();
			button5.getStyleClass().add("award-button");
			button5.setGraphic(new ImageView(image));
			button5.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/telefon.png");
					primaryStage2.close();
				}
			});
			grid.add(button5,1,1);
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/gra.png"));
			Button button6 = new Button();
			button6.getStyleClass().add("award-button");
			button6.setGraphic(new ImageView(image));
			button6.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/gra.png");
					primaryStage2.close();
				}
			});
			grid.add(button6,1,2);
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/pilka.png"));
			Button button7 = new Button();
			button7.getStyleClass().add("award-button");
			button7.setGraphic(new ImageView(image));
			button7.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/pilka.png");
					primaryStage2.close();
				}
			});
			grid.add(button7,2,0);
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/prezent.png"));
			Button button8 = new Button();
			button8.getStyleClass().add("award-button");
			button8.setGraphic(new ImageView(image));
			button8.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					award.setPath("/Ikony_i_css/prezent.png");
					primaryStage2.close();
				}
			});
			grid.add(button8,2,1);
			image = new Image(getClass().getResourceAsStream("/Ikony_i_css/samolot.png"));
			Button button9 = new Button();
			button9.getStyleClass().add("award-button");
			button9.setGraphic(new ImageView(image));
			button9.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					System.out.println("/Ikony_i_css/samolot.png");
					primaryStage2.close();
				}
			});
			grid.add(button9, 2, 2);
			


		
			Scene scene2 = new Scene(grid, 400, 400);
			scene2.getStylesheets().add(getClass().getResource("/Ikony_i_css/style.css").toExternalForm());

			primaryStage2.setResizable(false);
			primaryStage2.setScene(scene2);
			primaryStage2.show();

		
	}
    
    public void hide() {
		if (this.primaryStage != null) {
			this.primaryStage.hide();
		}
	}
	public void backToMenu()
	{
		Stage stage = new Stage();
		stage.setTitle("Nagrody");
		AwardControler award = new AwardControler();
		award.start(stage);
		primaryStage.close();
	}
}