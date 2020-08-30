/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.anjali.dao.ContentDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Comment;
import com.anjali.domain.Content;
import com.anjali.domain.Document;
import com.anjali.domain.Login;
import com.anjali.domain.Video;
import com.anjali.util.HibernateUtil;

/**
 * 
 * @author shekharkumar
 */
public class ContentDAOImpl implements ContentDAO {

    private final int pageSize = 3;
    private Session sessionObj;

    private Session getSession() {
	if (sessionObj != null && sessionObj.isOpen()) {
	    sessionObj = HibernateUtil.getSessionFactory().getCurrentSession();
	} else {
	    sessionObj = HibernateUtil.getSessionFactory().openSession();
	}
	return sessionObj;
    }

    public Content create(Content content) throws DBException {
	// Setting up default values;
	String searchKey = content.getHeading() + " " + content.getCity() + " "
		+ content.getSearchKeys() + " " + content.getCategoryName();
	content.setSearchKeys(searchKey);
	content.setCategoryName(content.getCategory().getName());

	Session session = getSession();
	session.beginTransaction();
	content.setCreatedAt(Calendar.getInstance().getTime());
	session.save(content);
	session.getTransaction().commit();
	session.close();
	return content;
    }

    public Content addDocument(long ideaId, Document document)
	    throws DBException {
	Session session = getSession();
	session.beginTransaction();

	// Saving document
	session.save(document);

	// Adding document to Content
	Content content = (Content) session.get(Content.class, ideaId);
	// idea.getDocuments().add(document);
	session.update(content);
	session.getTransaction().commit();
	session.close();
	return content;
    }

    public Content addVideo(long ideaId, Video video) throws DBException {
	Session session = getSession();
	session.beginTransaction();

	// Saving document
	session.save(video);

	// Adding document to Content
	Content content = (Content) session.get(Content.class, ideaId);
	content.getVideos().add(video);
	session.update(content);
	session.getTransaction().commit();
	session.close();
	return content;
    }

    public Content addComment(long contentId, Comment comment)
	    throws DBException {
	Session session = getSession();
	session.beginTransaction();

	// Saving comment
	comment.setCommentedAt(Calendar.getInstance().getTime());
	session.save(comment);

	// Adding document to Content
	Content content = (Content) session.get(Content.class, contentId);
	content.getComments().add(comment);
	session.update(content);
	session.getTransaction().commit();
	session.close();
	return content;
    }

    public Content update(Content content) throws DBException {
	Session session = getSession();
	session.beginTransaction();
	session.update(content);
	session.getTransaction().commit();
	session.close();
	return content;
    }

    public Content remove(Long id) throws DBException {
	throw new UnsupportedOperationException("Not supported yet."); // To
	// change
	// body
	// of
	// generated
	// methods,
	// choose
	// Tools
	// |
	// Templates.
    }

    public Content find(Long id) throws DBException {
	Session session = getSession();
	Content content = (Content) session.get(Content.class, id);
	// session.close();
	return content;
    }

    @SuppressWarnings("unchecked")
    public List<Content> findAll(int page) throws DBException {
	Session session = getSession();
	Query query = session
		.createQuery("Select c from Content c order by c.createdAt desc");
	// query.setFirstResult(((page * pageSize) - pageSize));
	// query.setMaxResults(pageSize);
	List<Content> contentList = query.list();
	// session.close();
	return contentList;
    }

    @SuppressWarnings("unchecked")
    public List<Content> findAllByLoginId(long id) throws DBException {
	Session session = getSession();
	Query query = session
		.createQuery("Select i from Content i where i.createdBy.id = ?");
	query.setParameter(0, id);
	List<Content> ideaList = query.list();
	// session.close();
	return ideaList;
    }

    @SuppressWarnings("unchecked")
    public List<Content> search(String searchString) throws DBException {
	Session session = getSession();
	List<Content> ideaList = null;

	String queryString = "Select c from Content c where c.searchKeys like ? or c.meterial like ?";
	Query query = session.createQuery(queryString);
	query.setParameter(0, "%" + searchString + "%");
	query.setParameter(1, "%" + searchString + "%");
	ideaList = query.list();
	// session.close();
	return ideaList;
    }

    @SuppressWarnings("unchecked")
    public List<Content> searchAdvance(long categoryId, String city,
	    long maxInvestment, long minProfit, int businessType, int page)
	    throws DBException {
	String queryString = null;
	queryString = "Select i from Content i where i.id > 0";

	if (categoryId > 0) {
	    queryString = queryString + " and i.category.id = " + categoryId;
	}
	if (city != null && !city.isEmpty() && !city.equalsIgnoreCase("All")) {
	    queryString = queryString + " and i.city like '%" + city.trim()
		    + "%'";
	}
	// Start Investment query conditions
	if (maxInvestment > 0 && maxInvestment == 1) {
	    queryString = queryString + " and i.maximumInvestment < 1";
	}
	if (maxInvestment > 0 && maxInvestment == 2) {
	    queryString = queryString
		    + " and i.maximumInvestment > 1 and i.maximumInvestment <= 5";
	}
	if (maxInvestment > 0 && maxInvestment == 3) {
	    queryString = queryString
		    + " and i.maximumInvestment > 5 and i.maximumInvestment <= 10";
	}
	if (maxInvestment > 0 && maxInvestment == 4) {
	    queryString = queryString
		    + " and i.maximumInvestment > 10 and i.maximumInvestment <= 20";
	}
	if (maxInvestment > 0 && maxInvestment == 5) {
	    queryString = queryString
		    + " and i.maximumInvestment > 20 and i.maximumInvestment <= 50";
	}
	if (maxInvestment > 0 && maxInvestment == 6) {
	    queryString = queryString
		    + " and i.maximumInvestment > 50 and i.maximumInvestment <= 100";
	}
	if (maxInvestment > 0 && maxInvestment == 7) {
	    queryString = queryString + " and i.maximumInvestment > 100";
	}
	// End Investment query conditions
	if (minProfit > 0) {
	    queryString = queryString + " and i.minimumProfit > " + minProfit;
	}
	if (businessType > 0) {
	    queryString = queryString + " and i.businessType = " + businessType;
	}

	System.out.println("queryString: " + queryString);
	Session session = getSession();
	Query query = session.createQuery(queryString);
	query.setFirstResult(((page * pageSize) - pageSize));
	query.setMaxResults(pageSize);
	List<Content> ideaList = query.list();
	// session.close();
	return ideaList;
    }

    public List<Content> searchByCategory(String category) throws DBException {
	throw new UnsupportedOperationException("Not supported yet."); // To
	// change
	// body
	// of
	// generated
	// methods,
	// choose
	// Tools
	// |
	// Templates.
    }

    @Override
    public int addLike(long contentId, String loginId) throws DBException {
	Session session = getSession();
	Transaction tx = session.beginTransaction();
	Login login = (Login) session.createQuery(
		"Select l from Login l where l.loginId = '" + loginId + "'")
		.uniqueResult();
	System.out.println("like: " + login);

	Content content = (Content) session.get(Content.class, new Long(
		contentId));
	System.out.println("content: " + content);

	content.addLike(login);

	session.update(content);
	content = (Content) session.get(Content.class, new Long(contentId));
	tx.commit();
	int totalLike = content.getLikes().size();
	session.close();
	return totalLike;
    }

}
