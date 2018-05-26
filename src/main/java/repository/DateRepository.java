package repository;

import org.hibernate.Session;
import org.hibernate.query.*;
import org.hibernate.query.Query;

import data.DateTask;

import java.util.Date;

public class DateRepository {
	private Session session;

	public DateRepository(Session session) {
		this.session = session;
	}

	public void addDateTask(DateTask date) {
		session.beginTransaction();
		session.persist(date);
		session.getTransaction().commit();
		session.close();
	}

}
