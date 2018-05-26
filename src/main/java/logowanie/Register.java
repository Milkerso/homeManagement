package logowanie;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.HibernateUtil;
import repository.UserRepository;

import java.io.File;
import java.security.MessageDigest;
//import java.util.*;
import java.security.NoSuchAlgorithmException;

import org.hibernate.Session;

import Application.MainApp;
import data.User;

public class Register extends Application {
	private MainApp mainApp;

	private Text formTitle, notification;
	private Label userMail, userPass, userPassCorrect;
	private static TextField userMailField;
	private static PasswordField userPassField, userPassFieldCorrect,adminPassField;
	private Label userFirstName, userLastName;
	private static TextField userFirstNameField;
	private static TextField userLastNameField;
	private CheckBox checkBox;
	String adminPass="Haslo123";
	
	// Uzytkownicy o1=new Uzytkownicy();

	// temporary

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Javafx Login Form");
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		this.initStage();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void logIn() {
		// if(userLoginField.getText().equals(login)&&
		// userPassField.getText().equals(password)) {
		// this.login=userLoginField.getText();
		Session session = HibernateUtil.getSessionFactory().openSession();
    	
    	
		  User user = new User();
		    	
		  if(checkBox.isSelected())
		  {
			  System.out.println("lol");
		  }
		if (checkAll() == true && !checkBox.isSelected()) {
			user.setMail(userMailField.getText());
			  user.setFirstName(userFirstNameField.getText());
			  user.setLastName(userLastNameField.getText());
			  user.setAdmin(false);
			  user.setPoints(0);
			  try {
				user.setPassword(sha1(userPassField.getText()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 UserRepository userRepo = new UserRepository(session);
		    	userRepo.addUser(user);
		//	System.out.println(lista.size());
	//		System.out.println(lista.get(0).mail);
			    	
			  	  session.close();
			this.mainApp.showLoginForm();
		}
		else if(checkAll()== true && checkBox.isSelected() && adminPassField.getText().equals("Haslo123"))
		{
			System.out.println("lol2");
			user.setMail(userMailField.getText());
			  user.setFirstName(userFirstNameField.getText());
			  user.setLastName(userLastNameField.getText());
			  user.setAdmin(true);
			  user.setPoints(0);
			  try {
				user.setPassword(sha1(userPassField.getText()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 UserRepository userRepo = new UserRepository(session);
		    	userRepo.addUser(user);
		//	System.out.println(lista.size());
	//		System.out.println(lista.get(0).mail);
			    	
			  	  session.close();
			this.mainApp.showLoginForm();
		}
		// }
		// else {
		// notification.setText("Zly login");
		// }
	}

	private void back() {
		this.mainApp.showLoginForm();
	}

	private void initStage() {
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
		// prepare grid
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(40);
		grid.setVgap(30);
		grid.setPadding(new Insets(40, 40, 40, 40));
		final ImageView imv = new ImageView();
        final Image image2 = new Image(new File("logo_do_logowania2").toURI().toString());
        imv.setImage(image2);
        
        final HBox pictureRegion = new HBox();
        pictureRegion.setAlignment(Pos.TOP_CENTER);
        pictureRegion.getChildren().add(imv);
		//grid.add(pictureRegion, 0, 8);
		
        formTitle = new Text("Welcome");
 		grid.add(formTitle, 0, 0, 2, 1);
 		formTitle.getStyleClass().add("welcome");
         
        
		userMail = new Label("User e-mail: ");
		grid.add(userMail, 0, 1);
		userMail.getStyleClass().add("userPassword");
		
		userMailField = new TextField();
		userMailField.setPromptText("Enter your e-mail");
		grid.add(userMailField, 1, 1);
		userMailField.getStyleClass().add("pass-field");
		
		userPass = new Label("User password:");
		grid.add(userPass, 0, 2);
		userPass.getStyleClass().add("userPassword");
		
		userPassField = new PasswordField();
		userPassField.setPromptText("Enter your password");
		grid.add(userPassField, 1, 2);
		userPassField.getStyleClass().add("pass-field");

		userPassCorrect = new Label("User password confirmed:");
		grid.add(userPassCorrect, 0, 3);
		userPassCorrect.getStyleClass().add("userPassword");

		userPassFieldCorrect = new PasswordField();
		userPassFieldCorrect.setPromptText("Confirm your password");
		grid.add(userPassFieldCorrect, 1, 3);
		userPassFieldCorrect.getStyleClass().add("pass-field");

		userFirstName = new Label("User first Name: ");
		grid.add(userFirstName, 0, 4);
		userFirstName.getStyleClass().add("userPassword");

		userFirstNameField = new TextField();
		userFirstNameField.setPromptText("Enter your First Name");
		grid.add(userFirstNameField, 1, 4);
		userFirstNameField.getStyleClass().add("pass-field");

		userLastName = new Label("User Last Name:");
		grid.add(userLastName, 0, 5);
		userLastName.getStyleClass().add("userPassword");

		userLastNameField = new TextField();
		userLastNameField.setPromptText("Enter your Last Name");
		grid.add(userLastNameField, 1, 5);
		userLastNameField.getStyleClass().add("pass-field");

		Button registerButton = new Button("Register");
		registerButton.getStyleClass().add("button-click");
		registerButton.setOnAction(event -> logIn());
		GridPane.setHalignment(registerButton, HPos.LEFT);
		registerButton.setMinWidth(160);
		registerButton.setMinHeight(35);
		grid.add(registerButton, 0, 7);
		
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("button-click");
		backButton.setOnAction(event -> back());
		backButton.setMinWidth(160);
		backButton.setMinHeight(35);
		GridPane.setHalignment(backButton, HPos.RIGHT);
		grid.add(backButton, 1, 7);
		
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

		notification = new Text("\n\n\n\n\n");
		grid.add(notification, 1, 8);

		AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setBottomAnchor(grid, 0.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setTopAnchor(grid, 80.0);
		AnchorPane.setRightAnchor(button1, 0.0);
		AnchorPane.setTopAnchor(button1, 0.0);
		anchorPane.getChildren().addAll(button1, grid);

		Scene scene = new Scene(anchorPane, 700, 800);
		scene.getStylesheets() .add(getClass().getResource("/Ikony_i_css/application.css").toExternalForm());

		// Pressing Enter works as pressing button "Sign in"
		grid.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				this.logIn();
			}
		});

		checkBox = new CheckBox("Administrtator?");
		 checkBox.setIndeterminate(false);
		 checkBox.getStyleClass().add("userPassword");
		 grid.add(checkBox, 0, 6);
		 checkBox.setOnAction(new EventHandler<ActionEvent>() {
	           @Override public void handle(ActionEvent e) {
	        	   adminPassField = new PasswordField();
	       		adminPassField.setPromptText("Enter admin password");
	       		grid.add(adminPassField, 1, 6);
	       		adminPassField.getStyleClass().add("pass-field");
	            }
	        });
		this.primaryStage.setMinHeight(275);
		this.primaryStage.setMinWidth(300);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void hide() {
		if (this.primaryStage != null) {
			this.primaryStage.hide();
		}
	}

	public static boolean checkName() {
		if (userFirstNameField.getText().length() < 3) {
			System.out.println(userFirstNameField.getText().length());
			return false;
		}
		for (int i = 0; i < userFirstNameField.getText().length(); i++) {
			if (userFirstNameField.getText().charAt(i) >= 65 && userFirstNameField.getText().charAt(i) <= 90) {

			} else if (userFirstNameField.getText().charAt(i) >= 97 && userFirstNameField.getText().charAt(i) <= 122) {

			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean checkLastName() {
		if (userLastNameField.getText().length() < 3) {
			return false;
		}
		for (int i = 0; i < userLastNameField.getText().length(); i++) {
			if (userLastNameField.getText().charAt(i) >= 65 && userLastNameField.getText().charAt(i) <= 90) {

			} else if (userLastNameField.getText().charAt(i) >= 97 && userLastNameField.getText().charAt(i) <= 122) {

			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean checkMail() {
		boolean tempMonkey = false;
		boolean tempDot = false;
		for (int i = 0; i < userMailField.getText().length(); i++) {
			if (userMailField.getText().charAt(i) <= 32 && userMailField.getText().charAt(i) >= 122) {
				return false;
			} else if (userMailField.getText().charAt(i) == 64)
				tempMonkey = true;
			else if (userMailField.getText().charAt(i) == 46)
				tempDot = true;
		}
		if (tempMonkey == true && tempDot == true)
			return true;
		else
			return false;
	}

	public static boolean checkPassword() {
		boolean tempBigChar = false;
		boolean tempSmallChar = false;
		boolean tempDigitChar = false;
		if (userPassField.getText().length() < 8) {
			return false;
		}
		for (int i = 0; i < userPassField.getText().length(); i++) {
			if (userPassField.getText().charAt(i) >= 65 && userPassField.getText().charAt(i) <= 90) {
				tempSmallChar = true;
			} else if (userPassField.getText().charAt(i) >= 97 && userPassField.getText().charAt(i) <= 122) {
				tempBigChar = true;
			} else if (userPassField.getText().charAt(i) >= 48 && userPassField.getText().charAt(i) <= 57) {
				tempDigitChar = true;
			} else {
				return false;
			}
		}
		if (tempSmallChar == true && tempBigChar == true && tempDigitChar == true) {
			return true;
		} else {
			return false;
		}
	}

	boolean checkPasswordCorrect() {
		if (userPassField.getText().equals(userPassFieldCorrect.getText()))
			return true;
		else
			return false;
	}

	boolean checkAll() {
		int c = 6;
		boolean adminPassword=true;
		StringBuilder builder = new StringBuilder("");
		if (checkPasswordCorrect() == false) {
			builder.append("Hasła się nie zgadzają\n");
			c--;
		}
		if (checkPassword() == false) {
			builder.append("").append("Złe hasło\n");
			c--;
		}
		if (checkMail() == false) {
			builder.append("Zły mail\n");
			c--;
		}
		if (checkName() == false) {
			builder.append("Złe imie\n");
			c--;
		}
		if (checkLastName() == false) {
			builder.append("Złe nazwisko\n");
			c--;
		}
		if(checkBox.isSelected()&&!adminPassField.getText().equals(adminPass)) {
			builder.append("Złe hasło administratora\n");
			adminPassword=false;
			c--;
		}
		for (int i = 0; i < c; i++) {
			builder.append("\n");
		}
		if (adminPassword==true &&checkPassword() == true && checkMail() == true && checkName() == true && checkLastName() == true&&checkPasswordCorrect()==true)
			return true;
		else {
			notification.setText(builder.toString());
			return false;
		}
	}
	public String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        } 
        return sb.toString();
    }
}

class Uzytkownicy {
	public String mail;
	public String password;
	public String firstName;
	public String lastName;
}
