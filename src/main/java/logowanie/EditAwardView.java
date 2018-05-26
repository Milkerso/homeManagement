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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.AwardRepository;
import repository.HibernateUtil;
import repository.TaskRepository;

public class EditAwardView extends Application
{
	Session session = HibernateUtil.getSessionFactory().openSession();
	AwardRepository taskRepo = new AwardRepository(session);
	private Text formTitle;
	private Label idLabel;
	private TextField idAwardField;
	private Label nameAwardLabel, countAwardLabel;
	private TextField nameAwardField, countAwardField;
	

	  ChoiceBox<String> choiceBox=new ChoiceBox<String>();
	  ObservableList<Award> items =(ObservableList<Award>) FXCollections.observableArrayList (taskRepo.getAll());
	
	  private double x=0;
	  private double y=0;
	  private Stage primaryStage;
	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage=primaryStage;
		primaryStage.setTitle("Edycja Nagrody");
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		this.initStage();
	}
	
	private void initStage()
	{
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(40, 40, 40, 40));

		
		nameAwardLabel = new Label("Nazwa nagrody: ");
		nameAwardLabel.getStyleClass().add("label-field");
		grid.add(nameAwardLabel, 0, 2);

		nameAwardField = new TextField();
		nameAwardField.getStyleClass().add("label-field");
		grid.add(nameAwardField, 1, 2);

		countAwardLabel = new Label("Koszt nagrody:");
		countAwardLabel.getStyleClass().add("label-field");
		grid.add(countAwardLabel, 0, 3);

		countAwardField = new TextField();
		countAwardField.getStyleClass().add("label-field");
		grid.add(countAwardField, 1, 3);
		    
        
        for(int i=0;i<items.size();i++)
        {
        choiceBox.getItems().add(items.get(i).toStringNameId());
        }
		formTitle = new Text("Edycja nagrody");
		grid.add(formTitle, 0, 0, 2, 1);
		
		
		idLabel = new Label("Nazwa nagrody: ");
		grid.add(idLabel, 0, 1);
		grid.add(choiceBox, 1, 1);
		
		Button deleteAwardButton = new Button("Edit");
		deleteAwardButton.getStyleClass().add("button-click");
		deleteAwardButton.setOnAction(event -> editAward());
		GridPane.setHalignment(deleteAwardButton, HPos.RIGHT);
		deleteAwardButton.setMinWidth(160);
		deleteAwardButton.setMinHeight(35);
		
		Button backAwardButton=new Button("Back");
		backAwardButton.getStyleClass().add("button-click");
		backAwardButton.setOnAction(event -> backAward());
		GridPane.setHalignment(backAwardButton, HPos.LEFT);
		backAwardButton.setMinWidth(160);
		backAwardButton.setMinHeight(35);
		
		
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
		
		choiceBox.setOnAction(event_-> uzupelnij());
		 choiceBox.getStyleClass().add("choice-box");
		grid.add(deleteAwardButton, 1, 7);
		grid.add(backAwardButton, 0, 7);
		Scene scene = new Scene(anchorPane, 500, 500);
		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());
		
		
		anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });
        
        //move around here
        anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            }
});
        primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
private void backAward()
{
	primaryStage.close();
	Stage stage = new Stage();
	stage.setTitle("Nagrody");
	AwardControler award = new AwardControler();
	award.start(stage);
}
private void editAward() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		AwardRepository taskRepo = new AwardRepository(session);

	  
		long a=0;
		int c=1000;

		
		for(int i=0;i<items.size()&&a==0;i++)
		{
			if(choiceBox.getValue().equals(items.get(i).toStringNameId()))
			{
				a=items.get(i).getId();
				c=i;
			}
		}
		items.get(c).setName(this.nameAwardField.getText());
		items.get(c).setCount(Integer.parseInt(countAwardField.getText()));
//		items.get(c).setComment(this.commentTaskField.getText());
//		items.get(c).setCountYang(Integer.parseInt(countYangTaskField.getText()));
		taskRepo.updateAward(a,c,items);
			    	
		session.close();
		primaryStage.close();
		Stage stage = new Stage();
		stage.setTitle("Nagrody");
		AwardControler award = new AwardControler();
		award.start(stage);
	}
private void uzupelnij()
{
	long a=0;
	int c=1000;
	for(int i=0;i<items.size()&&a==0;i++)
	{
		if(choiceBox.getValue().equals(items.get(i).toStringNameId()))
		{
			a=items.get(i).getId();
			c=i;
		}
	}
	nameAwardField.setText(items.get(c).getName());
//	descriptionTaskField.setText(items.get(c).getDescription());
//	commentTaskField.setText(items.get(c).getComment());
	countAwardField.setText(String.valueOf(items.get(c).getCount()));
}
}