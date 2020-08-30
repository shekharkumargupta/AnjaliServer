package com.anjali.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.anjali.constants.ApplicationConstants;
import com.anjali.dao.MessageDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Login;
import com.anjali.domain.Message;
import com.anjali.util.HibernateUtil;

public class MessageDAOImpl implements MessageDAO {

	
	private Session sessionObj;

	private Session getSession() {
		if (sessionObj != null && sessionObj.isOpen()) {
			sessionObj = HibernateUtil.getSessionFactory().getCurrentSession();
		} else {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
		}
		return sessionObj;
	}	
	
	@Override
	public Message create(Message message) throws DBException {
		System.out.println("MessageTo: "+message.getTo());
		Session session = getSession();
		session.beginTransaction();
		message.setSentAt(Calendar.getInstance().getTime());
		message.setStatus(ApplicationConstants.MESSAGE_STATUS.NEW.ordinal());
		session.save(message);
		session.getTransaction().commit();
		session.close();
		return message;
	}

	@Override
	public Message update(Message message) throws DBException {
		System.out.println(MessageDAOImpl.class.getName()+" >> update");
		Session session = getSession();
		session.beginTransaction();
		message.setStatus(ApplicationConstants.MESSAGE_STATUS.READ.ordinal());
		session.update(message);
		session.getTransaction().commit();
		session.close();
		return message;
	}

	@Override
	public Message remove(Long id) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		Message message = (Message) session.get(Message.class, id);
		session.delete(message);
		session.getTransaction().commit();
		session.close();
		return message;
	}

	@Override
	public Message find(Long id) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		Query query = session.createQuery("select m from Message where m.id = ?");
		query.setParameter(0, id);
		Message message = (Message) query.uniqueResult();
		session.getTransaction().commit();
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findAll() throws DBException {
		Session session = getSession();
		session.beginTransaction();
		Query query = session.createQuery("select m from Message m");
		List<Message> messages = query.list();
		session.getTransaction().commit();
		return messages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findAllTo(String loginId) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		Login login = (Login) session.createQuery("select l from Login l where l.loginId = ?")
				.setParameter(0, loginId).uniqueResult();
		Query query = session.createQuery("select m from Message m where m.to.id = ? order by m.sentAt desc")
				.setParameter(0, login.getId());
		List<Message> messages = query.list();
		session.getTransaction().commit();
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findAllFrom(String loginId) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		Login login = (Login) session.createQuery("select l from Login l where l.loginId = ?")
				.setParameter(0, loginId).uniqueResult();
		Query query = session.createQuery("select m from Message m where m.from.id = ? order by m.sentAt desc")
				.setParameter(0, login.getId());
		List<Message> messages = query.list();
		session.getTransaction().commit();
		return messages;
	}

}
