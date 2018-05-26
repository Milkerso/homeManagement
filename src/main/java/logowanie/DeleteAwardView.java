package logowanie;

import java.io.File;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import data.Award;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.AwardRepository;
import repository.HibernateUtil;
import repository.TaskRepository;

public class DeleteAwardView extends Application
{
	Session session = HibernateUtil.getSessionFactory().openSession();
	AwardRepository taskRepo = new AwardRepository(session);
	private Text formTitle;
	private Label idLabel;
	private TextField idTaskField;
	  ChoiceBox<String> choiceBox=new ChoiceBox<String>();
	  ObservableList<Award> items =(ObservableList<Award>) FXCollections.observableArrayList (taskRepo.getAll());
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage=primaryStage;
		primaryStage.setTitle("Usun nagrodę");
		this.initStage();
	}
	
	private void initStage()
	{
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(40, 40, 40, 40));
		final ImageView imv = new ImageView();
        final Image image2 = new Image(new File("2.png").toURI().toString());
        imv.setImage(image2);

        final HBox pictureRegion = new HBox();
        pictureRegion.getChildren().add(imv);
		//grid.add(pictureRegion, 0, 8);
      
        
        
       
        
        for(int i=0;i<items.size();i++)
        {
        choiceBox.getItems().add(items.get(i).toStringNameId());
        }
		Label form = new Label("Usuwanie nagrody");
		form.getStyleClass().add("form-title1");
		grid.add(form, 0, 0, 2, 1);
		
		
		idLabel = new Label("Nazwa nagrody: ");
		grid.add(idLabel, 0, 1);
		grid.add(choiceBox, 1, 1);
		Button deleteAwardButton = new Button("Delete");
		Button backAwardButton=new Button("Back");
		deleteAwardButton.getStyleClass().add("button-click");
		backAwardButton.getStyleClass().add("button-click");

		deleteAwardButton.setOnAction(event -> deleteAward());
		backAwardButton.setOnAction(event -> backAward());
		GridPane.setHalignment(deleteAwardButton, HPos.RIGHT);
		GridPane.setHalignment(backAwardButton, HPos.LEFT);
		grid.add(deleteAwardButton, 1, 3);
		grid.add(backAwardButton, 0, 3);
		
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

		AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setBottomAnchor(grid, 120.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setTopAnchor(grid, 80.0);
		AnchorPane.setRightAnchor(button1, 0.0);
		AnchorPane.setTopAnchor(button1, 0.0);
		anchorPane.getChildren().addAll(button1, grid);

		
		Scene scene = new Scene(anchorPane, 500, 500);
		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());
	
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
private void backAward()
{
	Stage stage = new Stage();
	stage.setTitle("Nagrody");
	AwardControler award = new AwardControler();
	award.start(stage);
	primaryStage.close();
}
private void deleteAward() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		AwardRepository taskRepo = new AwardRepository(session);
    	
	  
		long a=0;
		System.out.println(items.size());
		for(int i=0;i<items.size()&&a==0;i++)
		{
			if(choiceBox.getValue().equals(items.get(i).toStringNameId()))
			{
				a=items.get(i).getId();
			}
		}
		
		taskRepo.deleteAward(a);
			    	
		session.close();
		
		primaryStage.close();
		Stage stage = new Stage();
		stage.setTitle("Nagrody");
		AwardControler award = new AwardControler();
		award.start(stage);
		
	}

}