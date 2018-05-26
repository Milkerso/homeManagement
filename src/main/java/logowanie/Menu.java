package logowanie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import Application.MainApp;
import data.User;

public class Menu extends Application {

	private MainApp mainApp;

	private Stage primaryStage;

	private User user;

	public void start(Stage stage) {
		this.initStage(stage);
	}

	private void initStage(Stage stage) {
		this.primaryStage = stage;
		this.primaryStage.setTitle("Menu");

		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
		FXMLLoader loader = new FXMLLoader();
		try {
			user = mainApp.getUser();
			if(user.isAdmin()) {
			loader.setLocation(new File(path + "layout.fxml").toURI().toURL());
				
			}
			else
			{
				loader.setLocation(new File(path + "layoutUser.fxml").toURI().toURL());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		AnchorPane root = null;

		try {
			user = mainApp.getUser();
			root = loader.load();
			LayoutController controller = loader.getController();
			controller.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root, 1100, 800);
		this.primaryStage.setMinHeight(700);
		this.primaryStage.setMinWidth(1000);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void hide() {
		if (this.primaryStage != null) {
			this.primaryStage.hide();
		}
	}
}
