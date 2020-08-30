/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.anjali.dao.CategoryDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Category;
import com.anjali.util.HibernateUtil;

/**
 *
 * @author Ramesh
 */
public class CategoryDAOImpl implements CategoryDAO {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public Category create(Category category) throws DBException {
        Session session = getSession();
        session.beginTransaction();
        session.save(category);
        session.getTransaction().commit();
        return category;
    }

    public Category update(Category category) throws DBException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Category remove(Long id) throws DBException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Category find(Long id) throws DBException {
        return (Category) getSession().get(Category.class, id);
    }
    
    public Category findByName(String categoryName) throws DBException {
    	Session session = getSession();
        Query query = session.createQuery("Select c from Category c where c.name = ?")
        		.setParameter(0, categoryName);
        return (Category) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAll() throws DBException {
        Query query = getSession().createQuery("Select c from Category c");
        return query.list();
    }

	
}
