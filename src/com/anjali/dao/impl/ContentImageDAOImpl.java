/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.anjali.dao.ContentImageDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Content;
import com.anjali.domain.ContentImage;
import com.anjali.util.HibernateUtil;

/**
 *
 * @author shekharkumar
 */
public class ContentImageDAOImpl implements ContentImageDAO{

    private Session sessionObj;

    private Session getSession() {
        if (sessionObj != null && sessionObj.isOpen()) {
            sessionObj = HibernateUtil.getSessionFactory().getCurrentSession();
        } else {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
        }
        return sessionObj;
    }

    public ContentImage create(ContentImage image) throws DBException {
        Session session = getSession();
        session.beginTransaction();
        session.save(image);
        
        Content content = (Content) session.get(Content.class, image.getContentId());
        content.addImage(image);
    	content.setContainsImage(true);
        session.update(content);
        
        session.getTransaction().commit();
        session.close();
        return image;
    }

    public ContentImage update(ContentImage image) throws DBException {
        Session session = getSession();
        session.beginTransaction();
        session.merge(image);
        session.getTransaction().commit();
        session.close();
        return image;
    }

    public ContentImage findById(long id) throws DBException {
        String queryString = "select i from ContentImage i where i.id = "+id;
        Session session = getSession();
        Query query = session.createQuery(queryString);
        return (ContentImage) query.uniqueResult();
    }

    public ContentImage findByContentId(long contentId) throws DBException {
        String queryString = "select c from ContentImage c where c.contentId = "+contentId;
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery(queryString);
        ContentImage image = (ContentImage) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return image;
    }
    
    @SuppressWarnings("unchecked")
	public List<ContentImage> findAll() throws DBException{
        String queryString = "select i from ContentImage i";
        Session session = getSession();
        Query query = session.createQuery(queryString);
        return query.list();
    }
    
}
