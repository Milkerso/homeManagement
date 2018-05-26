package logowanie;

import org.hibernate.Session;

import Application.MainApp;
import data.Task;
import data.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import repository.HibernateUtil;
import repository.TaskRepository;
import repository.UserRepository;

public class LayoutController {

	static private ObservableList<Task> taskList;	//all tasks
	static private ObservableList<Task> taskList1;	//now
	static private ObservableList<Task> taskList2;	//completed
	static private ObservableList<Task> taskList0;	//planned
	static public User user;
	
	public User user4;
	private Task selectedTask=new Task();
	private boolean admin=true;
	
	//public static final ObservableList data = FXCollections.observableArrayList();
	@FXML
	private Button c;
	@FXML
	private Button calendarButton;
	@FXML
	private Button usersButton;
	@FXML
	private Button addTaskButton;
	@FXML
	private Button deleteTaskButton;
	@FXML
	private Button editTaskButton;
	@FXML
	private Button completeTaskButton;
	@FXML
	private Button helpButton;
	@FXML
	private ListView<Task> plannedTasksListView;
	@FXML
	private ListView<Task> completedTasksListView;
	@FXML
	private ListView<Task> nowTasksListView;
	@FXML
	private TabPane tabs;
	@FXML
	private Tab plannedTasksTab;
	@FXML
	private Tab nowTasksTab;
	@FXML
	private Tab completedTasksTab;
	@FXML
	private Label userLabel;
	@FXML
	public Label userLabel1;
	
	@FXML
	private Label taskNameLabel;
	@FXML
	private Label taskDescriptionLabel;
	@FXML
	private Label taskYangLabel;
	@FXML
	private Label taskCommentLabel;
	@FXML
	private Label descriptionLabel;	
	@FXML
	private Label yangLabel;
	@FXML
	private Label commentLabel;
	@FXML
	private Button signOut;
	@FXML
	private Button addAward;
	
	public void refresh()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		TaskRepository taskRepo = new TaskRepository(session);
		taskList = (ObservableList<Task>) FXCollections.observableArrayList(taskRepo.getAll());
		taskList0 =FXCollections.observableArrayList();
		taskList1 =FXCollections.observableArrayList();
		taskList2 =FXCollections.observableArrayList();
		
		updateTaskList();
		
		plannedTasksListView.setItems(taskList0);
		plannedTasksListView.setCellFactory(new TaskCellFactory());
		plannedTasksListView.getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends Task> ov, Task old_val, Task new_val) -> {
				showTask(new_val);
				selectedTask = new_val;
			}
		);
		
		plannedTasksListView.getSelectionModel().select(0);
		completedTasksListView.setItems(taskList2);
		completedTasksListView.setCellFactory(new TaskCellFactory());
		completedTasksListView.getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends Task> ov, Task old_val, Task new_val) -> {
				showTask(new_val);
				selectedTask = new_val;
			}
		);
		
		completedTasksListView.getSelectionModel().select(0);
		nowTasksListView.setItems(taskList1);
		nowTasksListView.setCellFactory(new TaskCellFactory());
		nowTasksListView.getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends Task> ov, Task old_val, Task new_val) -> {
				showTask(new_val);
				selectedTask = new_val;
			}
		);
		nowTasksListView.getSelectionModel().select(0);
		
		session.close();
	}
	@FXML
	void initialize() {
		this.addAward.setOnMouseClicked((event -> {
			addAwardView();
		}));
		this.helpButton.setOnMouseClicked((event -> {
			helpView();
		}));
		this.signOut.setOnMouseClicked((event -> {
			signOutAll();
			}));
		this.calendarButton.setOnMouseClicked((event -> {
			showCalendarMonthView();
		}));
		this.completeTaskButton.setOnMouseClicked((event->{
			choiceCompleteTaskButton();
		}));
		this.usersButton.setOnMouseClicked((event -> {
			showAllUsersView();
		}));

		this.addTaskButton.setOnMouseClicked((event -> {
			addTaskView();
		}));

		this.deleteTaskButton.setOnMouseClicked((event -> {
			deleteTaskView();
		}));

		this.editTaskButton.setOnMouseClicked((event -> {
			editAllTaskView();
		}));

		
		
		this.tabs.getSelectionModel().selectedItemProperty().addListener((ov, old_tab, new_tab) -> {
			deselectAll();
			updateTaskList();
			if (new_tab.equals(plannedTasksTab)) {
				completeTaskButton.setText("Wykonaj");
				System.out.println("lol");
				completeTaskButton.setDisable(false);
				this.completeTaskButton.setOpacity(1);
				plannedTasksListView.getSelectionModel().select(0);
			} else if (new_tab.equals(completedTasksTab)) {
				completeTaskButton.setText("Cofnij");
				completeTaskButton.setDisable(false);
				nowTasksListView.getSelectionModel().select(0);
				if(!user.isAdmin())
				{
					this.completeTaskButton.setDisable(true);
					this.completeTaskButton.setOpacity(0);
				}
			} else  {
				completeTaskButton.setText("Akceptuj");
				completedTasksListView.getSelectionModel().select(0);
				if(!user.isAdmin())
				{
					this.completeTaskButton.setDisable(true);
					this.completeTaskButton.setOpacity(0);
				}
			}
		});
		

	
	}
	
	 void deselectAll() {
		plannedTasksListView.getSelectionModel().clearSelection();
		nowTasksListView.getSelectionModel().clearSelection();
		completedTasksListView.getSelectionModel().clearSelection();
	}

	void showCalendarWeekView() {
		Stage stage = new Stage();
		stage.setTitle("Kalendarz");

		CalendarView calendarView = new CalendarView();

		calendarView.setTimeSlotLength(60);
		calendarView.start(stage);
	}
	
	void choiceCompleteTaskButton()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		UserRepository userRepo = new UserRepository(session);

		if(selectedTask.getState()==1)
		{
			User user2=new User();
			// ListView<User> listView = new ListView<User>();
//		       for(int i=0;i<items.size();i++)
//		       {
//		    	   System.out.print(items.get(i)+"");
//		    	   if(items.get(i).getId()==user.getId())
//		    	   {
//		    		  System.out.print(items.get(i)+"lol  ");
//		    	   }
//		       }
			user2=userRepo.getUserById(selectedTask.getIdUser());
			if(!user2.isAdmin())
			{
			user2.setPoints(selectedTask.getCountYang()+user2.getPoints());
			System.out.println(user2.toString());
			System.out.println(user2.getPoints());
			session = HibernateUtil.getSessionFactory().openSession();
			userRepo = new UserRepository(session);
			userRepo.updateUser(user2);
			}
		       
		     
		        selectedTask.setState(2);
		}
		else
		{
			
			selectedTask.setIdUser(user.getId());
			selectedTask.setState(1);
			
		}
		session = HibernateUtil.getSessionFactory().openSession();
		TaskRepository taskRepo = new TaskRepository(session);
		taskRepo.updateTask(selectedTask);
		LayoutController layout = new LayoutController();
		LayoutController.updateTaskList();
		refresh();
		session.close();
	}

	void showCalendarMonthView() {
		Stage stage = new Stage();
		stage.setTitle("Kalendarz");

		CalendarMonthView calendarView = new CalendarMonthView(user);

		calendarView.start(stage);
	}

	void addTaskView() {
		Stage stage = new Stage();
		stage.setTitle("Zadania");

		AddTaskView addtaskview = new AddTaskView();
		addtaskview.start(stage);
		refresh();
	}

	void showAllUsersView() {
		Stage stage = new Stage();
		stage.setTitle("Członkowie");

		AllUsersView alluserview = new AllUsersView();
		alluserview.start(stage);
	}

	void editAllUsersView() {
		Stage stage = new Stage();
		stage.setTitle("Członkowie");

		AllUsersView alluserview = new AllUsersView();
		alluserview.start(stage);
	}

	void editAllTaskView() {
		Stage stage = new Stage();
		stage.setTitle("Edit");

		EditTaskView editTaskView = new EditTaskView();
		editTaskView.setTask(selectedTask);
		editTaskView.start(stage);
	}

	void deleteTaskView() {
		Stage stage = new Stage();
		stage.setTitle("Zadania");

		DeleteTaskView deleteTaskView = new DeleteTaskView();
		deleteTaskView.setTask(selectedTask);
		deleteTaskView.start(stage);
	}
	
	public void setUser(User user) {
		this.user = user;
//		CalendarMonthView cmw=new CalendarMonthView();
//		cmw.setUser(user);
		try {
		System.out.println(user.getPoints());
		this.userLabel1.setText(String.valueOf(this.user.getPoints()));
		this.userLabel.setText("Zalogowano jako: " + this.user.getFirstName());
		refresh();
		}
		catch(NullPointerException e)
		{
		
		}
	}
	
	public void setPointss(User user)
	{
		this.user4=new User();
		//this.user4=user;
		Session session = HibernateUtil.getSessionFactory().openSession();
		UserRepository userRepo = new UserRepository(session);
		this.user4 = userRepo.getUserById(this.user.getId());
		if (this.userLabel1 == null) {
			this.userLabel1 = new Label(Long.toString(user4.getPoints()));
		} else {
		this.userLabel1.setText(Long.toString(this.user4.getPoints()));
			System.out.println(user4.getPoints());
			refresh();
			}
	}

	 static void updateTaskList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		TaskRepository taskRepo = new TaskRepository(session);
		taskList.setAll(taskRepo.getAll());
		taskList0.clear();
		taskList1.clear();
		taskList2.clear();
		for(int i=0;i<taskList.size();i++) {
			if (taskList.get(i).getState() == 0) {
				taskList0.add(taskList.get(i));
			} else if (taskList.get(i).getState() == 1) {
			if(user.isAdmin()|| user.getId()==taskList.get(i).getIdUser()){
				taskList1.add(taskList.get(i));
			}
			}
			else if(user.getId()==taskList.get(i).getIdUser()||user.isAdmin()){
			taskList2.add(taskList.get(i));
			}
		}
	}
	
	public void showTask(Task task) {
		if (task != null) {
			this.taskNameLabel.setText(task.getName());
			this.taskDescriptionLabel.setText(task.getDescription());
			this.taskYangLabel.setText(Integer.toString(task.getCountYang()));
//			if(task.getComment().length()<30)
//			{
				taskCommentLabel.setWrapText(true);
				taskCommentLabel.setTextAlignment(TextAlignment.JUSTIFY);
			this.taskCommentLabel.setText(task.getComment());
//			}
//			else if(task.getComment().length()>20&&task.getComment().length()<40)
//			{
//				taskCommentLabel.setWrapText(true);
//				taskCommentLabel.setTextAlignment(TextAlignment.JUSTIFY);
//				this.taskCommentLabel.setText(task.getComment().substring(0, 20)+task.getComment().substring(20, task.getComment().length()));
//			}
//			else
//			{
//				taskCommentLabel.setWrapText(true);
//				taskCommentLabel.setTextAlignment(TextAlignment.JUSTIFY);
//				this.taskCommentLabel.setText(task.getComment().substring(0, 20)+task.getComment().substring(20, 40)+task.getComment().substring(40, task.getComment().length()));
//			}
			this.descriptionLabel.setText("Opis: ");
			this.commentLabel.setText("Komentarz: ");
			this.yangLabel.setText("Punkty: ");
			this.completeTaskButton.setDisable(false);
			this.editTaskButton.setDisable(false);
			this.deleteTaskButton.setDisable(false);
		}
		else {
			this.taskNameLabel.setText("Brak zadań do wyświetlenia");
			this.taskDescriptionLabel.setText("");
			this.taskYangLabel.setText("");
			this.taskCommentLabel.setText("");
			this.descriptionLabel.setText("");
			this.commentLabel.setText("");
			this.yangLabel.setText("");
			this.completeTaskButton.setDisable(true);
			this.editTaskButton.setDisable(true); 
			this.deleteTaskButton.setDisable(true);
		}
		 try {
				if(!user.isAdmin())
					{
						this.addTaskButton.setDisable(true);
						this.completeTaskButton.setDisable(false);
						this.editTaskButton.setDisable(true);
						this.deleteTaskButton.setDisable(true);
					}
	      } catch (NullPointerException e) {
	         
	      }
//		if(!admin)
//		{
//			this.addTaskButton.setDisable(true);
//			this.completeTaskButton.setDisable(false);
//			this.editTaskButton.setDisable(true);
//			this.deleteTaskButton.setDisable(true);
//		}
//		
	
	}
	void addAwardView() {
		Stage stage = new Stage();
		stage.setTitle("Nagrody");
		

		if(user.isAdmin())
		{
			AwardControler award = new AwardControler();
			award.start(stage);
		}
		else
		{
			AllAwardView award=new AllAwardView(user);
			award.lc = this;
			award.start(stage);
		}

	}
	void helpView() {
		Stage stage = new Stage();
		stage.setTitle("Pomoc");
		System.out.println(user.toString());
		ShowHelpView award = new ShowHelpView();
		award.start(stage);
	}
		void signOutAll() {
			MainApp signout = new MainApp();
			Stage stage = (Stage) helpButton.getScene().getWindow();
			stage.close();
			signout.hideMainMenu();
			signout.showLoginForm();
		}
	

	class TaskCell extends ListCell<Task> {

		@Override
		public void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

			//int index = this.getIndex();
			String name = null;

			if (item == null || empty) {

			} else {
				name = /*(index + 1) + ". " +*/ item.getName();
			}

			this.setText(name);
			setGraphic(null);
		}
	}

	class TaskCellFactory implements Callback<ListView<Task>, ListCell<Task>> {

		@Override
		public ListCell<Task> call(ListView<Task> listView) {
			return new TaskCell();
		}
	}
}