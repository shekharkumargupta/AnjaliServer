/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao.impl;

import com.anjali.dao.EnquiryDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Enquiry;
import com.anjali.domain.Content;
import com.anjali.domain.Login;
import com.anjali.util.HibernateUtil;

import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author shekharkumar
 */
public class EnquiryDAOImpl implements EnquiryDAO {

    private Session sessionObj;

    private Session getSession() {
        if (sessionObj != null && sessionObj.isOpen()) {
            sessionObj = HibernateUtil.getSessionFactory().getCurrentSession();
        } else {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
        }
        return sessionObj;
    }

    public Enquiry create(Enquiry enquiry) throws DBException {
        enquiry.setDate(Calendar.getInstance().getTime());
        Session session = getSession();
        session.beginTransaction();
        if(enquiry.getEnquiredBy() != null){
            Login login = (Login) session.get(Login.class, enquiry.getEnquiredBy().getId());
            enquiry.setEnquiredBy(login);
        }
        if (enquiry.getContent() != null) {
            Content content = (Content) session.get(Content.class, enquiry.getContent().getId());
            enquiry.setContent(content);
        }
        session.save(enquiry);
        session.getTransaction().commit();
        return enquiry;
    }

    public Enquiry update(Enquiry enqury) throws DBException {
        Session session = getSession();
        session.beginTransaction();
        session.update(enqury);
        session.getTransaction().commit();
        session.close();
        return enqury;
    }

    public Enquiry find(long id) throws DBException {
        Session session = getSession();
        Query query = session.createQuery("Select e from Enquiry e where e.id = ?")
                .setParameter(0, id);
        return (Enquiry) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
	public List<Enquiry> findAll() throws DBException {
        Session session = getSession();
        Query query = session.createQuery("Select e from Enquiry e");
        List<Enquiry> enquiryList = query.list();
        return enquiryList;
    }

    @SuppressWarnings("unchecked")
	public List<Enquiry> findAllByLoginId(long loginId) throws DBException {
        Session session = getSession();
        Query query = session.createQuery("Select e from Enquiry e where e.enquiredBy.id = ?")
                .setParameter(0, loginId);
        List<Enquiry> enquiryList = query.list();
        return enquiryList;
    }

}
