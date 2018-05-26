package logowanie;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.HibernateUtil;
import repository.TaskRepository;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.hibernate.Session;

//import Application.MainApp;
import data.Task;

public class AddTaskView extends Application {

	private Label nameTaskLabel, descriptionTaskLabel, countYangTaskLabel, commentTaskLabel, choiceDate;
	private TextField nameTaskField, descriptionTaskField, countYangTaskField;
	private TextArea commentTaskField;
	private double x = 0;
	private double y = 0;
	private Date date = null;
	 private DatePicker checkInDatePicker;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Dodaj Nowe Zadanie");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		this.initStage();

	}

	private void addTask() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		TaskRepository taskRepo = new TaskRepository(session);

		Task task = new Task();

		task.setName(nameTaskField.getText());
		task.setComment(commentTaskField.getText());
		task.setCountYang(Integer.parseInt(countYangTaskField.getText()));
		task.setDescription(descriptionTaskField.getText());
		System.out.println(date);
		task.setDueDate(date);
		task.setState(0);

		taskRepo.addTask(task);

		LayoutController layout = new LayoutController();
		layout.updateTaskList();

		session.close();
		primaryStage.close();
	}
	
	private void date()
	{
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

		// final ImageView imv = new ImageView();
		// final Image image2 = new Image(new File("2.png").toURI().toString());
		// imv.setImage(image2);
		//
		// final HBox pictureRegion = new HBox();
		// pictureRegion.getChildren().add(imv);
		// grid.add(pictureRegion, 0, 8);

		// formTitle = new Text("Dodawanie zadania");
		// grid.add(formTitle, 0, 0, 2, 1);

		nameTaskLabel = new Label("Nazwa zadania: ");
		nameTaskLabel.getStyleClass().add("label-field");
		grid.add(nameTaskLabel, 0, 1);

		nameTaskField = new TextField();
		nameTaskField.getStyleClass().add("login-field");
		grid.add(nameTaskField, 1, 1);

		descriptionTaskLabel = new Label("Opis zadania:");
		descriptionTaskLabel.getStyleClass().add("label-field");
		grid.add(descriptionTaskLabel, 0, 2);

		descriptionTaskField = new TextField();
		descriptionTaskField.getStyleClass().add("login-field");
		grid.add(descriptionTaskField, 1, 2);

		countYangTaskLabel = new Label("Ilość punktów do zdobycia:");
		countYangTaskLabel.getStyleClass().add("label-field");
		grid.add(countYangTaskLabel, 0, 3);

		countYangTaskField = new TextField();
		countYangTaskField.getStyleClass().add("login-field");
		grid.add(countYangTaskField, 1, 3);

		commentTaskLabel = new Label("Uwagi do zadania: ");
		commentTaskLabel.getStyleClass().add("label-field");
		grid.add(commentTaskLabel, 0, 4);

		commentTaskField = new TextArea();
		grid.add(commentTaskField, 1, 4);
		commentTaskField.getStyleClass().add("text-area");
		
		

		Image image = new Image(getClass().getResourceAsStream("/Ikony_i_css/close2.png"));
		Button closeButton = new Button();
		closeButton.getStyleClass().add("close-button");
		closeButton.setGraphic(new ImageView(image));
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Stage stage = (Stage) closeButton.getScene().getWindow();
				stage.close();
			}
		});
		
		
		
		 checkInDatePicker = new DatePicker();
		 checkInDatePicker.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					//System.out.println(checkInDatePicker.getValue());
					
					 LocalDate isoDate =checkInDatePicker.getValue();
					 date = java.sql.Date.valueOf(isoDate);
					 System.out.println(date);
				}
			});
		 
		Button addTaskButton = new Button("DODAJ ZADANIE");
		addTaskButton.getStyleClass().add("button-click");
		GridPane.setHalignment(addTaskButton, HPos.RIGHT);
		addTaskButton.setOnAction(event -> addTask());

		AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setBottomAnchor(grid, 120.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setTopAnchor(grid, 80.0);
		AnchorPane.setRightAnchor(closeButton, 0.0);
		AnchorPane.setTopAnchor(closeButton, 0.0);
		AnchorPane.setRightAnchor(addTaskButton, 170.0);
		AnchorPane.setTopAnchor(addTaskButton, 380.0);
		anchorPane.getChildren().addAll(addTaskButton, closeButton, grid);

		Button backButton = new Button("WSTECZ");
		backButton.getStyleClass().add("button-click");
		backButton.setOnAction(event -> backToMenu());
		GridPane.setHalignment(backButton, HPos.LEFT);
		backButton.setMinWidth(160);
		backButton.setMinHeight(35);
		grid.add(backButton, 0, 8);
		
		
		choiceDate = new Label("Wybierz date ");
		choiceDate.getStyleClass().add("label-field");
		grid.add(choiceDate, 0, 6);      
		Button addDateButton = new Button("DODAJ DATĘ");
		addDateButton.getStyleClass().add("button-click");
		addDateButton.setOnAction(event -> addDate());
		addDateButton.setMinWidth(160);
		addDateButton.setMinHeight(35);
		GridPane.setHalignment(checkInDatePicker, HPos.RIGHT);
		grid.add(checkInDatePicker, 1, 6);

		Scene scene = new Scene(anchorPane, 550, 550);
		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());

		grid.add(addTaskButton, 1, 8);
		// Pressing Enter works as pressing button "Sign in"
//		grid.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//			if (event.getCode() == KeyCode.ENTER) {
//				this.addTask();
//			}
//		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void addDate() {
		Stage stage = new Stage();
		stage.setTitle("Kalendarz");

		AddDateController addDate = new AddDateController();
		addDate.setAddTaskView(this);

		try {
			addDate.start(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public void hide() {
		if (this.primaryStage != null) {
			this.primaryStage.hide();
		}
	}

	public void backToMenu() {
		primaryStage.close();
	}
}