/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao.impl;

import com.anjali.dao.DocumentDAO;
import com.anjali.domain.Document;
import com.anjali.util.HibernateUtil;

import org.hibernate.Session;

/**
 *
 * @author Ramesh
 */
public class DocumentDAOImpl implements DocumentDAO {

    private Session sessionObj;

    private Session getSession() {
        if (sessionObj != null && sessionObj.isOpen()) {
            sessionObj = HibernateUtil.getSessionFactory().getCurrentSession();
        } else {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
        }
        return sessionObj;
    }

    public Document findById(Long id) {
        Document document = (Document) getSession().get(Document.class, id);
        return document;
    }
}
