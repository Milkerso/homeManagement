package logowanie;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import data.Task;
import data.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logowanie.CalendarView.TimeSlot;
import repository.HibernateUtil;
import repository.TaskRepository;
 
public class WYSWIETL extends Application {
    
	// private final List<User> users = new ArrayList<>();
	Session session = HibernateUtil.getSessionFactory().openSession();
	TaskRepository taskRepo = new TaskRepository(session);
         
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zadania");        

        ListView<Task> listView = new ListView<Task>();
        ObservableList<Task> items =(ObservableList<Task>) FXCollections.observableArrayList (taskRepo.getAll());
        listView.setItems(items);
         
               
        StackPane root = new StackPane();
        root.getChildren().add(listView);
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();
    }
}