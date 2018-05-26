package logowanie;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class AddDateController extends Application {

	private AddTaskView addTaskView;

	@FXML
	private Stage primaryStage;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	@FXML
	void initialize() {
		this.okButton.setOnMouseClicked((event -> {
			confirm();
		}));
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Dodaj Nowe Zadanie");

		this.initStage();
	}

	private void initStage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("calendar.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();

		stage.setScene(scene);
		stage.show();
	}

	public void confirm() {
		LocalDate ld = datePicker.getValue();

		Date date = Date.valueOf(ld);
		if (addTaskView == null) {
			System.out.println("addtaskview is null");
		}
		if (date == null) {
			System.out.println("date is null");
		}
		this.addTaskView.setDate(date);

		primaryStage.close();
	}

	public void setAddTaskView(AddTaskView atv) {
		if (atv == null) {
			System.out.println("atv is null");
		}
		System.out.println("Powinno ustawic AddTaskView");
		this.addTaskView = atv;
		if (addTaskView == null) {
			System.out.println("addTaskView is null - nie ustawilo :(");
		}
	}
}
