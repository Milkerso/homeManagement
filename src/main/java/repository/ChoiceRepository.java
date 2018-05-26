package repository;

import org.hibernate.Session;
import org.hibernate.query.*;
import org.hibernate.query.Query;

import antlr.collections.List;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.*;

import data.Task;
import data.User;
import data.Award;
import data.Choice;
import javafx.collections.ObservableList;
import logowanie.EditTaskView;

public class ChoiceRepository {
	public ChoiceRepository(Session session) {
		this.session = session;
	}

	private Session session;

	public void addChoice(Choice choice) {
		session.beginTransaction();
		session.persist(choice);
		session.getTransaction().commit();
		session.close();

	}

	public void deleteChoice(int id) {
		session.beginTransaction();
		Choice choice = (Choice) session.get(Choice.class, id);
		session.delete(choice);
		session.getTransaction().commit();
		session.close();
	}

	public ArrayList<Choice> getAllAward() {
		session.beginTransaction();
		TypedQuery<Choice> query = session.createQuery("from Choice", Choice.class);
		ArrayList<Choice> choice = (ArrayList<Choice>) query.getResultList();
		return choice;
	}
	

}