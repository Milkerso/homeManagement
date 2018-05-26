package logowanie;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import data.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logowanie.CalendarView.TimeSlot;
import repository.HibernateUtil;
import repository.UserRepository;

public class ShowHelpView extends Application {

        public static double x=200;
 	   	public static double y=200;
		
    @Override
    public void start(Stage primaryStage) {
    
        primaryStage.setTitle("Użytkownicy");
        primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);
            
		
      
        Label label = new Label("Aplikacja Home management jest aplikacją, która ułatwia wykonywanie codziennych\n"
        		+ "czynności i zadań domowych. Aplikacja posiada dwa rodzaje użytkowników: Administratora\n"
        		+ "oraz użytkownika z podstawowymi prawami dostępu. Konto Administratora umożliwia\n"
        		+ "zadań oraz zarządzanie nimi, między innymi: edycję oraz usunięcie wybranego zadania.\n"
        		+ "Cała koncepcja tej aplikacji opiera się na współzawodnictwie członków rodziny, zarówno tych\n"
        		+ "najmłodszych, jak i tych starszych. Użytkownicy samodzielnie wybierają zadania, które chcą\n"
        		+ "wykonywać. Znajdują się one w głównym oknie 'Zadania do wykonia'.Jeśli, użytkownik wykonał\n"
        		+ "poszczególne zadanie naciska opcję 'Wykonaj'. Zadanie to przechodzi do sekcji 'W trakcie',\n"
        		+ "które następnie zatwierdza administrator i przechodzi do sekcji 'Zrobione'. Za wykonane\n"
        		+ "zadanie użytkownicy otrzymują punkty, które następnie mogą wymienić na nagrody.\n"
        		+ "Dokonać tego można w głównym oknie wybierając opcje 'Nagroda' i następnie dokonuje\n"
        		+ "wyboru, którą z nagród chce otrzymać.Za niewywiązanie się z zadania Administrator może\n"
        		+ "odjąć punkty użytkownikowi. Po więcej wejdź na stronę www.app-help.com.");
        
        label.getStyleClass().add("label");
        
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.setPadding(new Insets(20, 20, 20, 20));
        
        
        GridPane.setHalignment(label, HPos.CENTER);
        gridpane.add(label, 0, 2);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 720, 630);
		scene.getStylesheets() .add(getClass().getResource("/Ikony_i_css/style.css").toExternalForm());
		
		
		primaryStage.setScene(scene);
        primaryStage.show();
        
        
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
        
        Image logo = new Image(getClass().getResourceAsStream("/Ikony_i_css/logo_pomoc.png"));
        Button doLogo = new Button();
        doLogo.getStyleClass().add("button-image");
        doLogo.setGraphic(new ImageView(logo));
        
        gridpane.add(doLogo, 0, 0);
        GridPane.setHalignment(doLogo, HPos.CENTER);
        
        AnchorPane.setBottomAnchor(gridpane, 10.0);
		AnchorPane.setLeftAnchor(gridpane, 0.0);
		AnchorPane.setRightAnchor(gridpane, 0.0);
		AnchorPane.setTopAnchor(gridpane, 80.0);
        AnchorPane.setRightAnchor(button1, 0.0);
		AnchorPane.setTopAnchor(button1, 0.0);
		anchorPane.getChildren().addAll(button1, gridpane);
		
		anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });
		
		anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            }
});
    }
    
}