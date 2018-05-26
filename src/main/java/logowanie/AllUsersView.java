package logowanie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import data.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logowanie.CalendarView.TimeSlot;
import repository.HibernateUtil;
import repository.UserRepository;
 
public class AllUsersView extends Application {
    
	// private final List<User> users = new ArrayList<>();
	Session session = HibernateUtil.getSessionFactory().openSession();
	UserRepository userRepo = new UserRepository(session);
         
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Członkowie");
		primaryStage.initStyle(StageStyle.UNDECORATED);
    	String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
    	AnchorPane anchorPane = new AnchorPane();     
    	
    	Label label = new Label();
    	label.setText("Lista członków");
    	label.getStyleClass().add("label-czlonkowie");
        ListView<User> listView = new ListView<User>();
        ObservableList<User> items =(ObservableList<User>) FXCollections.observableArrayList (userRepo.getAll());
        listView.setItems(items);
        
        
        
        Scene scene = new Scene(anchorPane, 500, 519);
		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());
        anchorPane.getChildren().add(listView);
        listView.getStyleClass().add("list-view");
        primaryStage.setScene(scene);
        primaryStage.show();
        AnchorPane.setTopAnchor(listView, 150.0);
        AnchorPane.setLeftAnchor(listView, 40.0);
        AnchorPane.setRightAnchor(listView, 45.0);
        AnchorPane.setBottomAnchor(listView, 45.0);
        
        
        anchorPane.getChildren().add(label);
        AnchorPane.setTopAnchor(label, 90.0);
        AnchorPane.setLeftAnchor(label, 40.0);
        
        
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
		anchorPane.getChildren().add(button1);
		AnchorPane.setRightAnchor(button1, 0.0);
		AnchorPane.setTopAnchor(button1, 0.0);
    }
}