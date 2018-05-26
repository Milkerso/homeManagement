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
import javafx.collections.ObservableList;
import logowanie.EditTaskView;

public class TaskRepository {
	public TaskRepository(Session session) {
		this.session = session;
	}

	private Session session;

	public void addTask(Task task) {
		session.beginTransaction();
		session.persist(task);
		session.getTransaction().commit();
		session.close();

	}

	public void deleteTask(long id) {
		session.beginTransaction();
		Task task = (Task) session.get(Task.class, id);
		session.delete(task);
		session.getTransaction().commit();
		session.close();
	}

	public ArrayList<Task> getAll() {
		session.beginTransaction();
		TypedQuery<Task> query = session.createQuery("from Task", Task.class);
		ArrayList<Task> tasks = (ArrayList<Task>) query.getResultList();
		return tasks;
	}

	public ArrayList<Task> getNameId() {
		session.beginTransaction();
		TypedQuery<Task> query = session.createQuery("from Task", Task.class);
		ArrayList<Task> tasks = (ArrayList<Task>) query.getResultList();
		return tasks;
	}

	public void updateTask(long c, int id, ObservableList<Task> items) {
		session.beginTransaction();
		// EditTaskView edit=new EditTaskView();
		Task task = new Task();
		Task task1 = (Task) session.get(Task.class, c);
		session.delete(task1);
		// task.setId(task1.getId());
		task.setName(items.get(id).getName());
		task.setComment(items.get(id).getComment());
		task.setCountYang(items.get(id).getCountYang());
		task.setDescription(items.get(id).getDescription());

		session.persist(task);
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateTask(Task task) {
		session.beginTransaction();
		/*
		String s = "UPDATE Task SET name = :name " +
			   	   ", description = :description " +
				   ", comment = :comment " +
			   	   ", countyang = :countyang " + 
				   "WHERE id = :id";
		Query query = session.createQuery(s);
		query.setParameter("name", task.getName());
		query.setParameter("description", task.getDescription());
		query.setParameter("comment", task.getComment());
		query.setParameter("countyang", task.getCountYang());
		query.setParameter("id", task.getId());
		int result = query.executeUpdate();
		*/
		session.update(task);
		session.getTransaction().commit();
		session.close();
	}

}