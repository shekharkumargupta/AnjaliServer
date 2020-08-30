/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.anjali.constants.ApplicationConstants;
import com.anjali.dao.LoginDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Login;
import com.anjali.domain.UserType;
import com.anjali.util.HibernateUtil;

/**
 * 
 * @author Ramesh
 */
public class LoginDAOImpl implements LoginDAO {

	private Session sessionObj;

	private Session getSession() {
		if (sessionObj != null && sessionObj.isOpen()) {
			sessionObj = HibernateUtil.getSessionFactory().getCurrentSession();
		} else {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
		}
		return sessionObj;
	}

	public Login create(Login login) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		login.setPassword(ApplicationConstants.DEFAULT_PASSWORD);

		UserType userType = (UserType) session.get(UserType.class, 1);
		login.setActive(true);
		login.setUserType(userType);
		// Saving login
		session.save(login);
		session.getTransaction().commit();
		session.close();
		return login;
	}

	public Login update(Login login) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		session.update(login);
		session.getTransaction().commit();
		session.close();
		return login;
	}

	public Login remove(Long id) throws DBException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Login find(Long id) throws DBException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@SuppressWarnings("unchecked")
	public List<Login> findAll() throws DBException {
		Session session = getSession();
		List<Login> loginList = session.createQuery("Select l from Login l").list();
		session.close();
		return loginList;
	}

	public Login findByLoginId(String loginId) throws DBException {
		Session session = getSession();
		Query query = session.createQuery("Select l from Login l where l.loginId = '" + loginId + "'");
		Login login = (Login) query.uniqueResult();
		session.close();
		return login;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Login> findAllByProfession(String professionName) throws DBException {
		Session session = getSession();
		List<Login> loginList = session
				.createQuery("Select l from Login l where l.person.profession like '%" + professionName + "%'").list();
		session.close();
		return loginList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Login> search(String searchString) throws DBException {
		Session session = getSession();
		String queryString = "Select l from Login l where "
				+ "l.person.fullName like ? or "
				+ "l.person.email like ? or "
				+ "l.person.profession like ? or "
				+ "l.person.city like ? or "
				+ "l.person.mobileNumber like ? or "
				+ "l.person.jobProfile like ? or "
				+ "l.person.keySkills like ? ";
				
		List<Login> loginList = session
				.createQuery(queryString)
				.setParameter(0, "%" + searchString + "%")
				.setParameter(1, "%" + searchString + "%")
				.setParameter(2, "%" + searchString + "%")
				.setParameter(3, "%" + searchString + "%")
				.setParameter(4, "%" + searchString + "%")
				.setParameter(5, "%" + searchString + "%")
				.setParameter(6, "%" + searchString + "%")	
				.list();
		session.close();
		return loginList;
	}

	public Login verifyUser(String loginId, String password) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		Query query = session.createQuery(
				"Select l from Login l where  l.loginId = '" + loginId + "'" + " and l.password = '" + password + "' ");
		Login login  = (Login) query.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return login;
	}

	@Override
	public List<Login> friendList(String loginId) throws DBException {
		Session session = getSession();
		Login login = (Login) session.createQuery("Select l from Login l where l.loginId = '"+loginId+"'")
				.uniqueResult();
		List<Login> friends = login.getFriends();
		/*
		List<Login> friends = (List<Login>) session
				.createQuery("Select l.friends from Login l where l.loginId = ?")
				.setParameter(0, loginId).list();
		*/
		//session.close();
		return friends;
	}

}
