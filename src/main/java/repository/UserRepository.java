package repository;

import org.hibernate.Session;
//import org.hibernate.query.*;
//import org.hibernate.query.Query;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import data.Task;

//import org.hibernate.*;

import data.User;

public class UserRepository {
	public UserRepository(Session session) {
		this.session = session;
	}

	private Session session;

	public void addUser(User user) {
		session.beginTransaction();
		session.persist(user);
		session.getTransaction().commit();
		session.close();

	}

	public User getUser(String email) {
		session.beginTransaction();
		TypedQuery<User> query = session.createQuery("SELECT s FROM User s WHERE s.mail='" + email + "'", User.class);
		User user = query.getSingleResult();
		session.close();
		return user;
	}
	public User getUserById(long id) {
		session.beginTransaction();
		TypedQuery<User> query = session.createQuery("SELECT s FROM User s WHERE s.id='" + id + "'", User.class);
		User user = query.getSingleResult();
		session.close();
		return user;
	}
	public String getUserPassword(String mail) {
		session.beginTransaction();

		TypedQuery<User> query = session.createQuery("select s from User s where s.mail='" + mail + "'", User.class);
		User user = query.getSingleResult();
		// System.out.println("imie"+user.getFirstName());
		session.close();
		return user.getPassword();
	}

	public void deleteUser(Long id) {
		session.beginTransaction();
		User user = (User) session.get(User.class, id);
		session.delete(user);
		session.getTransaction().commit();
		session.close();

	}
	public void updateUser(User user) {
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
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
	public ArrayList<User> getAll() {
		session.beginTransaction();
		TypedQuery<User> query = session.createQuery("from User", User.class);
		ArrayList<User> users = (ArrayList<User>) query.getResultList();
		return users;
	}
}
