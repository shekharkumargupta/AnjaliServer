package com.anjali.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.anjali.dao.ShareBoxDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Content;
import com.anjali.domain.Login;
import com.anjali.domain.ShareBox;
import com.anjali.util.HibernateUtil;

public class ShareBoxDAOImpl implements ShareBoxDAO {

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
	public ShareBox create(ShareBox shareBox) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		session.save(shareBox);
		session.getTransaction().commit();
		session.close();
		return shareBox;
	}

	@Override
	public ShareBox create(long contentId, String sharedByLoginId) throws DBException {
		Session session = getSession();
		session.beginTransaction();

		Login sharedBy = (Login) session.createQuery("select l from Login l where l.loginId = ?")
				.setParameter(0, sharedByLoginId).uniqueResult();
		Content content = (Content) session.createQuery("select c from Content c where c.id = ?")
				.setParameter(0, contentId).uniqueResult();

		System.out.println("ShareBox >> Create");
		System.out.println("sharedBy: " + sharedByLoginId);
		System.out.println("content: " + content);

		ShareBox shareBox = new ShareBox();
		shareBox.setContent(content);
		shareBox.setSharedBy(sharedBy);
		shareBox.setCreatedAt(Calendar.getInstance().getTime());
		session.save(shareBox);

		session.getTransaction().commit();
		session.close();
		return shareBox;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> contentsByLoginId(String loginId) throws DBException {
		Session session = getSession();
		Login login = (Login) session.createQuery("select l from Login l where l.loginId = ?").setParameter(0, loginId)
				.uniqueResult();

		System.out.println("contentsByLoginId: " + login);

		Query query = session
				.createQuery("select s from ShareBox s LEFT JOIN s.sharedTo t where t.id = ? order by s.createdAt desc")
				.setParameter(0, login.getId());

		List<ShareBox> shareBoxList = (List<ShareBox>) query.list();
		List<Content> contentList = new ArrayList<Content>();
		for (ShareBox box : shareBoxList) {
			Content c = box.getContent();
			c.setSharedBy(box.getSharedBy());
			contentList.add(c);
		}
		return contentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> sharedByFriends(String loginId) throws DBException {
		Session session = getSession();
		Login login = (Login) session.createQuery("select l from Login l where l.loginId = ?").setParameter(0, loginId)
				.uniqueResult();

		System.out.println("contentsByLoginId: " + login);
		List<Content> contentList = new ArrayList<Content>();
		if (login.getFriends() != null && login.getFriends().size() > 0) {
			Query query = session
					.createQuery("select s from ShareBox s where s.sharedBy in(:friends) order by s.createdAt desc")
					.setParameterList("friends", login.getFriends());

			List<ShareBox> shareBoxList = (List<ShareBox>) query.list();
			for (ShareBox box : shareBoxList) {
				Content c = box.getContent();
				c.setSharedBy(box.getSharedBy());
				contentList.add(c);
			}
			
		}
		return contentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Login> getSharer(long contentId) throws DBException {
	    Session session = getSession();
	    session.beginTransaction();
	    Query query = session.createQuery("select s.sharedBy from ShareBox s where s.content.id = ?")
		    .setParameter(0, contentId);
	    List<Login> loginList = query.list();


	    List<Login> proxyList = new ArrayList<>();
	    for(Login l: loginList){
		Login login = new Login();
		login.setId(l.getId());
		login.setLoginId(l.getLoginId());
		login.setPerson(l.getPerson());
		login.setActive(l.isActive());
		login.setUserType(l.getUserType());
		login.setRequested(l.isRequested());
		login.setProfilePrivacyPolicy(l.getProfilePrivacyPolicy());
		proxyList.add(login);
	    }

	    session.getTransaction().commit();
	    return proxyList;
	}


}
