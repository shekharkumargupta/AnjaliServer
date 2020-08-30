package com.anjali.dao.impl;

import org.hibernate.Session;

import com.anjali.dao.ProfileImageDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Login;
import com.anjali.domain.ProfileImage;
import com.anjali.util.HibernateUtil;

public class ProfileImageDAOImpl implements ProfileImageDAO {

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
	public ProfileImage create(ProfileImage image) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		session.save(image);
		session.getTransaction().commit();
		session.close();
		return image;
	}

	@Override
	public ProfileImage update(ProfileImage image) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		session.update(image);
		session.getTransaction().commit();
		session.close();
		return image;
	}

	@Override
	public ProfileImage findByLoginId(String loginId) throws DBException {
		Session session = getSession();
		Login login = (Login) session.createQuery("select l from Login l where l.loginId =:loginId")
				.setParameter("loginId", loginId).uniqueResult();

		ProfileImage image = (ProfileImage) session
				.createQuery("select p from ProfileImage p where p.person.id =:personId ")
				.setParameter("personId", login.getPerson().getId()).uniqueResult();
		session.close();
		return image;
	}
}
