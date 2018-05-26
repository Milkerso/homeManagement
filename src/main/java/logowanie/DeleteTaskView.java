package logowanie;

import java.io.File;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import data.Task;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.HibernateUtil;
import repository.TaskRepository;

public class DeleteTaskView extends Application {
	Session session = HibernateUtil.getSessionFactory().openSession();
	TaskRepository taskRepo = new TaskRepository(session);
	private Text formTitle;
	private Label idLabel;
	//private TextField idTaskField;
	//ChoiceBox<String> choiceBox = new ChoiceBox<String>();
	//ObservableList<Task> items = (ObservableList<Task>) FXCollections.observableArrayList(taskRepo.getAll());
	private Stage primaryStage;
	private double x = 250;
	private double y = 250;
	private Task task;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Usun zadanie");
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
		final ImageView imv = new ImageView();
		final Image image2 = new Image(new File("2.png").toURI().toString());
		imv.setImage(image2);

		final HBox pictureRegion = new HBox();
		pictureRegion.getChildren().add(imv);
		// grid.add(pictureRegion, 0, 8);

//		for (int i = 0; i < items.size(); i++) {
//			choiceBox.getItems().add(items.get(i).toStringNameId());
//		}
		Label form = new Label("Usuwanie zadania");
		form.getStyleClass().add("form-title1");
		grid.add(form, 0, 0, 2, 1);

		idLabel = new Label("Nazwa zadania: ");
		grid.add(idLabel, 0, 1);
//		grid.add(choiceBox, 1, 1);
		Button deleteTaskButton = new Button("Delete");
		deleteTaskButton.getStyleClass().add("button-click");
		Button backTaskButton = new Button("Back");
		backTaskButton.getStyleClass().add("button-click");
		deleteTaskButton.setOnAction(event -> deleteTask());
		backTaskButton.setOnAction(event -> backTask());
		GridPane.setHalignment(deleteTaskButton, HPos.RIGHT);
		GridPane.setHalignment(backTaskButton, HPos.LEFT);
		grid.add(deleteTaskButton, 1, 3);
		grid.add(backTaskButton, 0, 3);
		
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
		
		
		Scene scene = new Scene(anchorPane, 400, 400);
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

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void backTask() {
		primaryStage.close();
	}

	private void deleteTask() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		TaskRepository taskRepo = new TaskRepository(session);

		//long a = 0;
//		System.out.println(items.size());
//		for (int i = 0; i < items.size() && a == 0; i++) {
//			if (choiceBox.getValue().equals(items.get(i).toStringNameId())) {
//				a = items.get(i).getId();
//			}
//		}

		//taskRepo.deleteTask(a);
		taskRepo.deleteTask(task.getId());

		LayoutController layout = new LayoutController();
		LayoutController.updateTaskList();
		session.close();
		primaryStage.close();
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
