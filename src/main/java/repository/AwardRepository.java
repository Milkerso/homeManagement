package repository;

import org.hibernate.Session;
import org.hibernate.query.*;
import org.hibernate.query.Query;

import antlr.collections.List;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.*;

import data.Award;
import data.Task;
import data.User;
import javafx.collections.ObservableList;
import logowanie.EditTaskView;

public class AwardRepository {
	public AwardRepository(Session session) {
		this.session = session;
	}

	private Session session;

	public void addAward(Award award) {
		session.beginTransaction();
		session.persist(award);
		session.getTransaction().commit();
		session.close();

	}

	public void deleteAward(long id) {
		session.beginTransaction();
		Award award = (Award) session.get(Award.class, id);
		session.delete(award);
		session.getTransaction().commit();
		session.close();
	}

	public ArrayList<Award> getAll() {
		session.beginTransaction();
		TypedQuery<Award> query = session.createQuery("from Award", Award.class);
		ArrayList<Award> awards = (ArrayList<Award>) query.getResultList();
		return awards;
	}

	public ArrayList<Award> getNameId() {
		session.beginTransaction();
		TypedQuery<Award> query = session.createQuery("from Award", Award.class);
		ArrayList<Award> awards = (ArrayList<Award>) query.getResultList();
		return awards;
	}

	public void updateAward(long c, int id, ObservableList<Award> items) {
		session.beginTransaction();
		// EditTaskView edit=new EditTaskView();
		Award award = new Award();
		Award award1 = (Award) session.get(Award.class, c);
		session.delete(award1);
		// task.setId(task1.getId());
		award.setName(items.get(id).getName());
		award.setCount(items.get(id).getCount());



		session.persist(award);
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateAward(Award award) {
		session.beginTransaction();
		String s = "UPDATE Award SET name = :name " +
			   	   ", count = :count " + 
				   "WHERE id = :id";
		Query query = session.createQuery(s);
		query.setParameter("name", award.getName());
//		query.setParameter("description", task.getDescription());
//		query.setParameter("comment", task.getComment());
		query.setParameter("count", award.getCount());
		query.setParameter("id", award.getId());
		int result = query.executeUpdate();
		session.close();
	}
	public ArrayList<Award> getAllAward() {
		session.beginTransaction();
		TypedQuery<Award> query = session.createQuery("from Award", Award.class);
		ArrayList<Award> awards = (ArrayList<Award>) query.getResultList();
		return awards;
	}

}