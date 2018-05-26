package logowanie;

import data.Task;
import data.User;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.HibernateUtil;
import repository.TaskRepository;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.Session;

public class CalendarMonthView extends Application {

	// First day of current month
	private LocalDate month = LocalDate.now();
	private Label monthNameLabel = new Label();
	private LocalDate firstSlotStart;// = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),1);
	// One time slot = one day
	// Period ==> days, weeks, months, years
	// Duration ==> seconds, minutes, days
	private final Period slotLength = Period.ofDays(1);
	// private final Duration slotLength = Duration.ofDays(1);
	private LocalDate lastSlotStart;// = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth() - 1);
	public User user1;
	private BorderPane bp;

	private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

	private List<TimeSlot> timeSlots;

	Session session = HibernateUtil.getSessionFactory().openSession();
	TaskRepository taskRepo = new TaskRepository(session);
	ObservableList<Task> taskList = (ObservableList<Task>) FXCollections.observableArrayList(taskRepo.getAll());
	CalendarMonthView(User user) {
		this.user1=user;
	}
	@Override
	public void start(Stage primaryStage) {
		GridPane calendarView = this.getMonthGrid();
		calendarView.getStyleClass().add("grid-calendar");
		monthNameLabel.setText(month.getMonth().name() + " " + month.getYear());
		monthNameLabel.getStyleClass().add("monthlabel");

		// ScrollPane scroller = new ScrollPane(calendarView);
		// AnchorPane ap = new AnchorPane(this.calendarView);
		bp = new BorderPane();
		
		Insets insetsLeft = new Insets(0, 0, 0, 160);
		Insets insetsRight = new Insets(0, 160, 0, 0);
		Insets insetsBottom = new Insets(0, 20, 20, 20);

		double offset = 4;
		Image image = new Image(getClass().getResourceAsStream("/Ikony_i_css/prev.png"));
		Button prevMonthButton = new Button();
		prevMonthButton.getStyleClass().add("button1");
		prevMonthButton.setGraphic(new ImageView(image));
		prevMonthButton.setOnAction(event -> {
			month = month.minusMonths(1);
			bp.setBottom(getMonthGrid());
			monthNameLabel.setText(month.getMonth().name() + " " + month.getYear());
		});
		Image image1 = new Image(getClass().getResourceAsStream("/Ikony_i_css/next.png"));
		Button nextMonthButton = new Button();
		nextMonthButton.getStyleClass().add("button1");
		nextMonthButton.setGraphic(new ImageView(image1));
		AnchorPane.setRightAnchor(nextMonthButton, offset);
		nextMonthButton.setOnAction(event -> {
			month = month.plusMonths(1);
			bp.setBottom(getMonthGrid());
			monthNameLabel.setText(month.getMonth().name() + " " + month.getYear());
		});
		AnchorPane.setLeftAnchor(monthNameLabel, offset);
		AnchorPane.setRightAnchor(monthNameLabel, offset);

		// ap.getChildren().addAll(prevMonthButton, nextMonthButton, monthNameLabel);
		AnchorPane.setBottomAnchor(calendarView, 0.0);
		AnchorPane.setTopAnchor(calendarView, 40.0);
		AnchorPane.setLeftAnchor(calendarView, 0.0);
		AnchorPane.setRightAnchor(calendarView, 0.0);
		
		bp.setBottom(calendarView);
		bp.setLeft(prevMonthButton);
		bp.setAlignment(prevMonthButton,Pos.CENTER_LEFT);
		BorderPane.setMargin(prevMonthButton, insetsLeft);
		bp.setRight(nextMonthButton);
		bp.setAlignment(nextMonthButton,Pos.CENTER_RIGHT);
		BorderPane.setMargin(nextMonthButton, insetsRight);
		bp.setCenter(monthNameLabel);
		
		BorderPane.setMargin(calendarView, insetsBottom);
		
		Scene scene = new Scene(bp);
		scene.getStylesheets().add(getClass().getResource("calendar-view.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Kalendarz");
		
		primaryStage.show();
		

		// this.showTasks();

		// set after showing primaryStage
		// so that ap width and height are already set
		primaryStage.setMinWidth(bp.getWidth() + 200);
		primaryStage.setMinHeight(bp.getHeight() + 100);
	}

	private GridPane getMonthGrid() {
		firstSlotStart = LocalDate.of(month.getYear(), month.getMonth(), 1);
		lastSlotStart = LocalDate.of(month.getYear(), month.getMonth(), month.lengthOfMonth() - 1);
		

		GridPane calendarView = new GridPane();

		ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

		LocalDate today = month;
		LocalDate startOfMonth = today.minusDays(today.getDayOfMonth() - 1);
		LocalDate endOfMonth = startOfMonth.plusDays(today.getMonth().maxLength() - 1);

		LocalDate startOfWeek = startOfMonth.minusDays(startOfMonth.getDayOfWeek().getValue() - 1);
		timeSlots = new ArrayList<>();

		int slotIndex = 1;
		LocalDate date = startOfWeek;
		// przechodzenie po tygodniach
		// for (LocalDate date = startOfWeek; !date.isAfter(endOfMonth); /*dateMonth =
		// dateMonth.plusDays(7)*/) {
		for (int i = 0; i < 6; ++i) { // zawsze wyświetla 6 tygodni w miesiącu

			// numer tygodnia w pierwszej kolumnie
			int weekNumber = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
			Label label = new Label(Integer.toString(weekNumber));
			label.getStyleClass().add("label-tygodni");
			label.setPadding(new Insets(2));
			StackPane stackPane = new StackPane();
			stackPane.getChildren().add(label);
			stackPane.getStyleClass().add("week-header");
			GridPane.setHalignment(stackPane, HPos.RIGHT);
			calendarView.add(stackPane, 0, slotIndex);

			// dni tygodnia
			LocalDate endOfWeek = date.plusDays(6);
			for (/* LocalDate date = date */ ; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
				TimeSlot timeSlot = new TimeSlot(date, slotLength);
				timeSlots.add(timeSlot);

				registerDragHandlers(timeSlot, mouseAnchor);

				calendarView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), slotIndex);
			}

			slotIndex++;
		}
		 Image image = new Image(getClass().getResourceAsStream("/Ikony_i_css/poniedzialek.png"));
		 Image image2 = new Image(getClass().getResourceAsStream("/Ikony_i_css/wtorek.png"));
		 Image image3 = new Image(getClass().getResourceAsStream("/Ikony_i_css/sroda.png"));
		 Image image4 = new Image(getClass().getResourceAsStream("/Ikony_i_css/czwartek.png"));
		 Image image5 = new Image(getClass().getResourceAsStream("/Ikony_i_css/piatek.png"));
		 Image image6 = new Image(getClass().getResourceAsStream("/Ikony_i_css/sobota.png"));
		 Image image7 = new Image(getClass().getResourceAsStream("/Ikony_i_css/niedziela.png"));
	     Button button1 = new Button();
	     Button button2 = new Button();
	     Button button3 = new Button();
	     Button button4 = new Button();
	     Button button5 = new Button();
	     Button button6 = new Button();
	     Button button7 = new Button();
	     button1.setGraphic(new ImageView(image));
	     button2.setGraphic(new ImageView(image2));
	     button3.setGraphic(new ImageView(image3));
	     button4.setGraphic(new ImageView(image4));
	     button5.setGraphic(new ImageView(image5));
	     button6.setGraphic(new ImageView(image6));
	     button7.setGraphic(new ImageView(image7));
	  
	     button1.setMaxWidth(Double.MAX_VALUE);
	     button2.setMaxWidth(Double.MAX_VALUE);
	     button3.setMaxWidth(Double.MAX_VALUE);
	     button4.setMaxWidth(Double.MAX_VALUE);
	     button5.setMaxWidth(Double.MAX_VALUE);
	     button6.setMaxWidth(Double.MAX_VALUE);
	     button7.setMaxWidth(Double.MAX_VALUE);
			calendarView.add(button1,1, 0);
			calendarView.add(button2,2, 0);
			calendarView.add(button3,3, 0);
			calendarView.add(button4,4, 0);
			calendarView.add(button5,5, 0);
			calendarView.add(button6,6, 0);
			calendarView.add(button7,7, 0);
		

		
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setHgrow(Priority.NEVER);
		ColumnConstraints c2 = new ColumnConstraints();
		c2.setHgrow(Priority.ALWAYS);

		RowConstraints r1 = new RowConstraints();
		r1.setVgrow(Priority.NEVER);
		RowConstraints r2 = new RowConstraints();
		r2.setVgrow(Priority.ALWAYS);

		calendarView.getColumnConstraints().addAll(c1, c2, c2, c2, c2, c2, c2, c2);
		calendarView.getRowConstraints().addAll(r1, r2, r2, r2, r2, r2);

		this.showTasks();

		return calendarView;
	}

	private void registerDragHandlers(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
		timeSlot.getView().setOnMouseClicked(event -> {
			// mouseAnchor.set(timeSlot);
			// timeSlot.getView().startFullDrag();
			timeSlots.forEach(slot -> slot.setSelected(slot == timeSlot));
		});
		/*
		 * timeSlot.getView().setOnMouseDragEntered( event -> { TimeSlot startSlot =
		 * mouseAnchor.get(); //timaSlots.forEach(isBetween()); });
		 */
		timeSlot.getView().setOnMouseReleased(event -> mouseAnchor.set(null));
	}

	private void showTasks() {
		// Session session = HibernateUtil.getSessionFactory().openSession();
		// TaskRepository taskRepo = new TaskRepository(session);
		// javax.persistence.TypedQuery<Task> query = session.createQuery("FROM Task
		// WHERE duedate = Month(Now())");
		// ObservableList<Task> taskList = (ObservableList<Task>)
		// FXCollections.observableArrayList(taskRepo.getAll());
		// ObservableList<Task> taskList = (ObservableList<Task>)
		// FXCollections.observableArrayList(query.getResultList());
		System.out.println(user1.getFirstName());
		for (Task task : taskList) {
			if(user1.isAdmin())	
			{
				this.addTask(task);
			}
				else if (!user1.isAdmin())
			{
				if(user1.getId()==task.getIdUser()||task.getIdUser()==0)
				this.addTask(task);
			}
		}
	}

	public boolean addTask(Task task) {
		LocalDate date = task.getDueDate().toLocalDate();
		// checks if this task can be place in this month
		if (date.getMonth().equals(this.firstSlotStart.getMonth()) && date.getYear() == this.firstSlotStart.getYear()) {
			for (TimeSlot timeSlot : this.timeSlots) {
				if (timeSlot.start.getDayOfMonth() == date.getDayOfMonth()) {
					timeSlot.addTask(task);
					return true;
				}
			}
		}
		return false;
	}

	private class TimeSlot {
		private final LocalDate start;
		private final Period duration;
		private final VBox view;
		private final Label dayNumber;

		// tasks for that day
		private final List<Task> tasks = new ArrayList<>();
		private final VBox tasksGrid;

		private final BooleanProperty selected = new SimpleBooleanProperty();

		public final BooleanProperty selectedProperty() {
			return this.selected;
		}

		public final boolean isSelected() {
			return this.selected.get();
		}

		public final void setSelected(boolean selected) {
			this.selectedProperty().set(selected);
		}

		public TimeSlot(LocalDate start, Period duration) {
			this.start = start;
			this.duration = duration;

			// view for a day in the month
			view = new VBox();
			view.setMinSize(80, 80);
			view.getStyleClass().add("time-slot");

			// number of that day in month
			dayNumber = new Label();
			dayNumber.setText(Integer.toString(start.getDayOfMonth()));
			if (start.getMonth() == month.getMonth()) {
				// days belong to currently view month
				dayNumber.getStyleClass().add("day-number");
			} else {
				// days from other months
				dayNumber.getStyleClass().add("day-number-other");
			}
			dayNumber.setPadding(new Insets(1));

			// pane containing tasks for that day
			tasksGrid = new VBox();
			tasksGrid.getStyleClass().add("tasks-grid");
			tasksGrid.setPadding(new Insets(2, 0, 0, 0));

			view.getChildren().addAll(dayNumber, tasksGrid);

			selectedProperty().addListener((obs, wasSelected, isSelected) -> {
				view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected);
			});
		}

		private void displayTasks() {
			tasksGrid.getChildren().clear();
			tasksGrid.setSpacing(2);
			final int MAX_TASKS_DISPLAYED = 2;
			int rowIndex = 0;

			for (Task task : tasks) {
				if (rowIndex >= (MAX_TASKS_DISPLAYED + 1)) {
					break;
				} else if (rowIndex >= MAX_TASKS_DISPLAYED) {
					int remainingTasks = tasks.size() - MAX_TASKS_DISPLAYED;
					Label label = new Label("Pozostałych: " + Integer.toString(remainingTasks));
					AnchorPane pane = new AnchorPane(label);
					pane.getStyleClass().add("task-pane-info");
					// GridPane.setRowIndex(pane, rowIndex++);
					tasksGrid.getChildren().add(pane);
				} else {
					Label label = new Label(task.getName());
					AnchorPane pane = new AnchorPane(label);
					if(task.getState()==0)
					{
					pane.getStyleClass().add("task-pane0");
					}
					else if(task.getState()==1)
					{
						pane.getStyleClass().add("task-pane");
					}
					else {
						pane.getStyleClass().add("task-pane2");
					}
					// GridPane.setRowIndex(pane, rowIndex++);
					tasksGrid.getChildren().add(pane);
				}
			}
		}

		public void addTask(Task task) {
			this.tasks.add(task);
			this.displayTasks();
		}

		public LocalDate getStart() {
			return start;
		}

		public Node getView() {
			return this.view;
		}

		public DayOfWeek getDayOfWeek() {
			return this.start.getDayOfWeek();
		}
	}

	public static void main(String args[]) {
		launch(args);
	}
}
