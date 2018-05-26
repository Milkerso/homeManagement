package logowanie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import Application.MainApp;
import data.Award;
import data.Choice;
import data.Task;
import data.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import logowanie.LayoutController;

 
public class AllAwardView extends Application {
	@FXML
	public Label userLabel1;
	private Stage primaryStage;
	private TextField nameAwardField;
	public LayoutController lc;
    public int a = 0;
    List<Integer> lista = new ArrayList<Integer>();
	Session session = HibernateUtil.getSessionFactory().openSession();
	AwardRepository userRepo = new AwardRepository(session);
	User user2=new User();
	
	public AllAwardView(User user)
	{
		this.user2=user;
	}
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
		

		ObservableList<Award> items =(ObservableList<Award>) FXCollections.observableArrayList (userRepo.getAllAward());
		
		
		int j = 0;
		int t = 0;
		for(int i=0 ;i<items.size();i++)
		{
		Image image = new Image(getClass().getResourceAsStream(items.get(i).getPath()));
		Button button =  new Button();
		image = new Image(getClass().getResourceAsStream(items.get(i).getPath()));
		button.getStyleClass().add("award-button");
		button.setGraphic(new ImageView(image));

		button.setId(String.valueOf(i));
		this.a = items.get(i).getCount();
		System.out.println(button.getId());
		lista.add(items.get(i).getCount());
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println(lista.get(Integer.parseInt(button.getId())));
				choiceAward(lista.get(Integer.parseInt(button.getId())),Integer.parseInt(button.getId()));
				//primaryStage.close();
				//LayoutController ly=new LayoutController();
			
			}
		});
		Label nameAwardLabel = new Label();
		nameAwardLabel.getStyleClass().add("form-title1");
		if(items.get(i).getName().length()>10)
		nameAwardLabel.setText(items.get(i).getName().substring(0, 10) +"\n"+ items.get(i).getCount()+" $" );
		else
			nameAwardLabel.setText(items.get(i).getName() +"\n"+ items.get(i).getCount()+" $" );
		if(t%3==0) 
		{
			j=j+2;
			t=0;
		}
		
		grid.add(button,t,j);
		grid.add(nameAwardLabel,t,j+1);	

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
		Image images = new Image(getClass().getResourceAsStream("/Ikony_i_css/prize1.png"));
		 Button button1 = new Button();
        button1.getStyleClass().add("close-button");
        button1.setGraphic(new ImageView(image));
        button1.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
        	   Stage stage = (Stage) button1.getScene().getWindow();
        	   stage.close();
            }
        });
        Stage stage=new Stage();
    	ChoiceAwardUser h = new ChoiceAwardUser();
        Button button2 = new Button();
        button2.getStyleClass().add("close-button");
        button2.setGraphic(new ImageView(images));
        button2.setOnAction(event -> {
        	h.start(stage);
			primaryStage.close();
			});
		AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setBottomAnchor(grid, 120.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setTopAnchor(grid, 80.0);
		AnchorPane.setRightAnchor(button1, 0.0);
		AnchorPane.setTopAnchor(button1, 0.0);
		AnchorPane.setRightAnchor(button2, 0.0);
		AnchorPane.setBottomAnchor(button2, 0.0);
		anchorPane.getChildren().addAll(button1,button2, grid);
		Scene scene2 = new Scene(anchorPane, 800, 800);
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
          
            
            
           
    		Label form = new Label("Potwierdz wybor nagrody");
    		form.getStyleClass().add("form-title1");
    		grid.add(form, 0, 0, 2, 1);
    		
//    		
//    		Label idLabel = new Label("Nazwa nagrody: ");
//    		grid.add(idLabel, 0, 1);
    		
    		Button deleteAwardButton = new Button("Potwierdz");
    		Button backAwardButton=new Button("Wstecz");
    		deleteAwardButton.getStyleClass().add("button-click");
    		backAwardButton.getStyleClass().add("button-click");

    		deleteAwardButton.setOnAction(event -> chAward(a,primaryStage2,c));
    		backAwardButton.setOnAction(event -> backAward(primaryStage2));
    		GridPane.setHalignment(deleteAwardButton, HPos.CENTER);
    		GridPane.setHalignment(backAwardButton, HPos.CENTER);
    		grid.add(deleteAwardButton, 1, 2);
    		grid.add(backAwardButton, 0, 2);
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
    		Scene scene2 = new Scene(anchorPane, 400, 400);
    		scene2.getStylesheets().add(new File(path + "style.css").toURI().toString());
    	
    		primaryStage2.initStyle(StageStyle.UNDECORATED);
    		primaryStage2.setResizable(false);
    		primaryStage2.setScene(scene2);
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
		lc.setPointss(user2);
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