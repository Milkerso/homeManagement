package logowanie;

import java.io.File;

import org.hibernate.Session;

import data.Task;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.HibernateUtil;
import repository.TaskRepository;

public class EditTaskView extends Application {
	Session session = HibernateUtil.getSessionFactory().openSession();
	TaskRepository taskRepo = new TaskRepository(session);
	private Text formTitle;
	private Label idLabel;
	private Label nameTaskLabel, descriptionTaskLabel, countYangTaskLabel, commentTaskLabel;
	private TextField nameTaskField, descriptionTaskField, countYangTaskField, commentTaskField;
	private Task task;

	private double x = 0;
	private double y = 0;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Edycja Zadania");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		this.initStage();
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

		nameTaskLabel = new Label("Nazwa zadania: ");
		nameTaskLabel.getStyleClass().add("label-field");
		grid.add(nameTaskLabel, 0, 2);

		nameTaskField = new TextField();
		nameTaskField.getStyleClass().add("login-field");
		grid.add(nameTaskField, 1, 2);

		descriptionTaskLabel = new Label("Opis zadania:");
		descriptionTaskLabel.getStyleClass().add("label-field");
		grid.add(descriptionTaskLabel, 0, 3);

		descriptionTaskField = new TextField();
		descriptionTaskField.getStyleClass().add("login-field");
		grid.add(descriptionTaskField, 1, 3);

		countYangTaskLabel = new Label("Ilość punktów do zdobycia:");
		countYangTaskLabel.getStyleClass().add("label-field");
		grid.add(countYangTaskLabel, 0, 4);

		countYangTaskField = new TextField();
		countYangTaskField.getStyleClass().add("login-field");
		grid.add(countYangTaskField, 1, 4);

		commentTaskLabel = new Label("Uwagi do zadania: ");
		commentTaskLabel.getStyleClass().add("label-field");
		grid.add(commentTaskLabel, 0, 5);

		commentTaskField = new TextField();
		commentTaskField.getStyleClass().add("login-field");
		grid.add(commentTaskField, 1, 5);

//		for (int i = 0; i < items.size(); i++) {
//			choiceBox.getItems().add(items.get(i).toStringNameId());
//		}
		Label form = new Label("Edycja zadania");
		form.getStyleClass().add("form-title1");
		grid.add(form, 0, 0, 2, 1);
		
		idLabel = new Label("Nazwa zadania: ");
		//grid.add(idLabel, 0, 1);
//		grid.add(choiceBox, 1, 1);

		Button deleteTaskButton = new Button("Edit");
		deleteTaskButton.getStyleClass().add("button-click");
		deleteTaskButton.setOnAction(event -> editTask());
		GridPane.setHalignment(deleteTaskButton, HPos.RIGHT);
		deleteTaskButton.setMinWidth(160);
		deleteTaskButton.setMinHeight(35);

		Button backTaskButton = new Button("Back");
		backTaskButton.getStyleClass().add("button-click");
		backTaskButton.setOnAction(event -> backTask());
		GridPane.setHalignment(backTaskButton, HPos.LEFT);
		backTaskButton.setMinWidth(160);
		backTaskButton.setMinHeight(35);

		Image image = new Image(getClass().getResourceAsStream("/Ikony_i_css/close2.png"));
		Button button1 = new Button();
		button1.getStyleClass().add("close-button");
		button1.setGraphic(new ImageView(image));
		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Stage stage = (Stage) button1.getScene().getWindow();
				stage.close();
			}
		});

		AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setBottomAnchor(grid, 120.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setTopAnchor(grid, 80.0);
		AnchorPane.setRightAnchor(button1, 0.0);
		AnchorPane.setTopAnchor(button1, 0.0);
		anchorPane.getChildren().addAll(button1, grid);

//		choiceBox.setOnAction(event_ -> uzupelnij());

		grid.add(deleteTaskButton, 1, 7);
		grid.add(backTaskButton, 0, 7);
		Scene scene = new Scene(anchorPane, 500, 500);
		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());

		anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				x = event.getSceneX();
				y = event.getSceneY();
			}
		});

		// move around here
		anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - x);
				primaryStage.setY(event.getScreenY() - y);
			}
		});
		
		this.uzupelnij();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void backTask() {
		primaryStage.close();
	}

	private void editTask() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		TaskRepository taskRepo = new TaskRepository(session);
		// Task task=new Task();

//		long a = 0;
//		int c = 1000;
//		// System.out.println(items.size());
//
//		for (int i = 0; i < items.size() && a == 0; i++) {
//			if (choiceBox.getValue().equals(items.get(i).toStringNameId())) {
//				a = items.get(i).getId();
//				c = i;
//			}
//		}
//		items.get(c).setName(this.nameTaskField.getText());
//		items.get(c).setDescription(this.descriptionTaskField.getText());
//		items.get(c).setComment(this.commentTaskField.getText());
//		items.get(c).setCountYang(Integer.parseInt(countYangTaskField.getText()));
//		taskRepo.updateTask(a, c, items);
		
		this.task.setName(this.nameTaskField.getText());
		this.task.setDescription(this.descriptionTaskField.getText());
		this.task.setComment(this.commentTaskField.getText());
		this.task.setCountYang(Integer.valueOf(this.countYangTaskField.getText()));
		
		taskRepo.updateTask(task);
		LayoutController layout = new LayoutController();
		LayoutController.updateTaskList();

		session.close();
		primaryStage.close();
	}

	private void uzupelnij() {
//		long a = 0;
//		int c = 1000;
//		for (int i = 0; i < items.size() && a == 0; i++) {
//			if (choiceBox.getValue().equals(items.get(i).toStringNameId())) {
//				a = items.get(i).getId();
//				c = i;
//			}
//		}
//		nameTaskField.setText(items.get(c).getName());
//		descriptionTaskField.setText(items.get(c).getDescription());
//		commentTaskField.setText(items.get(c).getComment());
//		countYangTaskField.setText(String.valueOf(items.get(c).getCountYang()));
		
		nameTaskField.setText(task.getName());
		descriptionTaskField.setText(task.getDescription());
		commentTaskField.setText(task.getComment());
		countYangTaskField.setText(Integer.toString(task.getCountYang()));
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
}
