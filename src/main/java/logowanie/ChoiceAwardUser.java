package logowanie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import data.Award;
import data.Choice;
import data.Task;
import data.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logowanie.CalendarView.TimeSlot;
import repository.AwardRepository;
import repository.ChoiceRepository;
import repository.HibernateUtil;
import repository.TaskRepository;
import repository.UserRepository;

 
public class ChoiceAwardUser extends Application {
	
	private Stage primaryStage;
	private TextField nameAwardField;
    public int a = 0;
    List<Integer> lista = new ArrayList<Integer>();
	Session session = HibernateUtil.getSessionFactory().openSession();
	ChoiceRepository choiceRepo = new ChoiceRepository(session);
	User user2=new User();

    @Override
    public void start(Stage primaryStage)
    {
    	this.primaryStage = primaryStage;
        primaryStage.setTitle("Wybierz nagrodę");    
        this.initStage();
    }
    private void initStage() 
    {
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5));
		grid.setHgap(30);
		grid.setVgap(25);
		grid.setPadding(new Insets(40, 40, 40, 40));
		final ImageView imv = new ImageView();
		final Image image2 = new Image(new File(path + "logo_do_logowania2.png").toURI().toString());
		imv.setImage(image2);
		
		final HBox pictureRegion = new HBox();
		pictureRegion.setAlignment(Pos.TOP_CENTER);
		pictureRegion.getChildren().add(imv);
		

		ObservableList<Choice> items =(ObservableList<Choice>) FXCollections.observableArrayList (choiceRepo.getAllAward());
		
		
		int j = 0;
		int t = 0;
		for(int i=0 ;i<items.size();i++)
		{
		Image image = new Image(getClass().getResourceAsStream(items.get(i).getPathn()));
		Button button =  new Button();
		image = new Image(getClass().getResourceAsStream(items.get(i).getPathn()));
		button.getStyleClass().add("award-button");
		button.setGraphic(new ImageView(image));
		System.out.println(button.getId());
		lista.add(items.get(i).getId());
		button.setId(String.valueOf(i));
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
	    		Stage primary= new Stage();
	    		 primary.setTitle("Odbior nagrody");
	     		GridPane grid = new GridPane();
	     		grid.setPadding(new Insets(5));
	     		grid.setAlignment(Pos.CENTER);
	     		grid.setHgap(10);
	     		grid.setVgap(10);
	     		grid.setPadding(new Insets(40, 40, 40, 40));
	     		Label form = new Label("Potwierdz");
	    		form.getStyleClass().add("form-title1");
;
	     		Button b=new Button("Usuń");
	     		//System.out.println(button.getId());
	     		b.setId(button.getId());
	     		b.getStyleClass().add("button-click");
	     		grid.add(b, 1, 1);
	     		Button back=new Button("Wroc");
	     		back.getStyleClass().add("button-click");
	     		grid.add(back, 0, 1);
	    		b.setOnAction(new EventHandler<ActionEvent>() {
	    			@Override
	    			public void handle(ActionEvent e) {
	    				Session session = HibernateUtil.getSessionFactory().openSession();
	    				ChoiceRepository choiceRep = new ChoiceRepository(session);
	    					session = HibernateUtil.getSessionFactory().openSession();
	    					choiceRep = new ChoiceRepository(session);
	    					System.out.println(lista.get(Integer.parseInt(b.getId())));
	    					choiceRep.deleteChoice((lista.get(Integer.parseInt(b.getId()))));
	    					session.close();
	    					primaryStage.close();
	    					primary.close();
	    					Stage stage = new Stage();
	    					stage.setTitle("Nagrody");
//	    					AwardControler award = new AwardControler();
//	    					award.start(stage);
	    					
	    			}});
	    		back.setOnAction(new EventHandler<ActionEvent>() {
	    			@Override
	    			public void handle(ActionEvent e) {
	    				
	    					primaryStage.close();
	    					primary.close();
	    					Stage stage = new Stage();
	    					stage.setTitle("Nagrody");
//	    					AwardControler award = new AwardControler();
//	    					award.start(stage);
	    					
	    			}});
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
	     		button.getStyleClass().add("award-button");
	    		grid.add(form, 0, 0, 2, 1);
	    		Scene scene = new Scene(anchorPane, 400, 400);
	    		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());
	    	
	    		primary.initStyle(StageStyle.UNDECORATED);
	    		primary.setScene(scene);
	    		primary.show();
			}
		});
		Label nameAwardLabel = new Label();
		Label nameAwardLabel1 = new Label();
		StringBuilder a=new StringBuilder();
		String newline = System.getProperty("line.separator");
		a.append(items.get(i).getNagroda()).append(newline).append(items.get(i).getNameuser());
		nameAwardLabel1.setText(items.get(i).getNagroda());
		nameAwardLabel.setText(a.toString());
		if(t%3==0)
		{
			j=j+2;
			t=0;
		}
		
		grid.add(button,t,j);
		grid.add(nameAwardLabel1,t,j+1);	
	//	grid.add(nameAwardLabel1,t,j+2);	

		t++;
		}
		
		
		
		
//		for(int i=0 ;i<items.size();i++)
//		{
//		System.out.println(items.get(i).getPath());		
//		Image image = new Image(getClass().getResourceAsStream(items.get(i).getPath()));
//
//		Button button[] =  new Button[i];
//		image = new Image(getClass().getResourceAsStream(items.get(i).getPath()));
//		
//		button[i].getStyleClass().add("award-button");
//		button[i].setGraphic(new ImageView(image));
//		
//		
//		button[i].setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent e) {
//			//	award.setPath("/Ikony_i_css/50.png");
//			//	primaryStage2.close();
//			}
//		});
//		grid.add(button[i],i,0);
//		}
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
		Scene scene2 = new Scene(anchorPane, 600, 600);
		scene2.getStylesheets().add(new File(path + "style.css").toURI().toString());
	
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene2);
		primaryStage.show();


		
    	
    }

//        ListView<Award> listView = new ListView<Award>();
//        ObservableList<Award> items =(ObservableList<Award>) FXCollections.observableArrayList (userRepo.getAllAward());
//        listView.setItems(items);
//         
//             
//        StackPane root = new StackPane();
//        root.getChildren().add(listView);
//        primaryStage.setScene(new Scene(root, 200, 350));
//        primaryStage.show();
    public void hide() {
		if (this.primaryStage != null) {
			this.primaryStage.hide();
		}
	}
    public void choiceAward(int a, int c) 
    	{
    		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
    		Stage primaryStage2 = new Stage();
	        primaryStage2.setTitle("Potwierdzenie");
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
          
            
            
           
    		Text formTitle = new Text("Potwierdz wybor nagrody");
    		grid.add(formTitle, 0, 0, 2, 1);
    		
//    		
//    		Label idLabel = new Label("Nazwa nagrody: ");
//    		grid.add(idLabel, 0, 1);
    		
    		Button deleteAwardButton = new Button("Potwierdz");
    		Button backAwardButton=new Button("Wstecz");
    		deleteAwardButton.getStyleClass().add("close-button");
    		backAwardButton.getStyleClass().add("close-button");

    		deleteAwardButton.setOnAction(event -> chAward(a,primaryStage2,c));
    		backAwardButton.setOnAction(event -> backAward(primaryStage2));
    		GridPane.setHalignment(deleteAwardButton, HPos.CENTER);
    		GridPane.setHalignment(backAwardButton, HPos.CENTER);
    		grid.add(deleteAwardButton, 1, 2);
    		grid.add(backAwardButton, 0, 2);
    		Scene scene = new Scene(grid, 200, 200);
    		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());
    	

    		primaryStage2.setScene(scene);
    		primaryStage2.show();
    	}
    public void chAward(int a,Stage primaryStage2, int c)
    {
    	if(user2.getPoints()-a<0)
    	{
    		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Ikony_i_css\\";
    		Stage primary= new Stage();
    		 primary.setTitle("Potwierdzenie");
     		GridPane grid = new GridPane();
     		grid.setPadding(new Insets(5));
     		grid.setAlignment(Pos.CENTER);
     		grid.setHgap(10);
     		grid.setVgap(10);
     		grid.setPadding(new Insets(40, 40, 40, 40));
     		Text formTitle = new Text("Brak Monet");
    		grid.add(formTitle, 0, 0, 2, 1);
    		Scene scene = new Scene(grid, 200, 200);
    		scene.getStylesheets().add(new File(path + "style.css").toURI().toString());
    	

    		primary.setScene(scene);
    		primary.show();
    		primaryStage2.close();
    		
    	}
    	else{
    	user2.setPoints(user2.getPoints()-a);
 
    	Session session = HibernateUtil.getSessionFactory().openSession();
		UserRepository userRepo = new UserRepository(session);
			session = HibernateUtil.getSessionFactory().openSession();
			userRepo = new UserRepository(session);
			userRepo.updateUser(user2);
			session.close();
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		ChoiceRepository choiceRep = new ChoiceRepository(session2);
		session2 = HibernateUtil.getSessionFactory().openSession();
		Choice choice=new Choice();
		AwardRepository awardRep = new AwardRepository(session2);
		ObservableList<Award> items2 =(ObservableList<Award>) FXCollections.observableArrayList (awardRep.getAllAward());
		choice.setNagroda(items2.get(c).getName());
		choice.setPathn(items2.get(c).getPath());
		choice.setNameuser(user2.getFirstName()+" "+user2.getLastName());
//		choice.setNagroda("aaa");
//		choice.setPathn("aaaa");
//		choice.setNameuser("lol");
		choiceRep.addChoice(choice);
		session2.close();
    	primaryStage2.close();
    	}
    }
    private void backAward(Stage primaryStage2)
    {
    	
    	primaryStage2.close();
    }
    
	public void backToMenu()
	{
		primaryStage.close();
	}
}