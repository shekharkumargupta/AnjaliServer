/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao.impl;

import com.anjali.dao.SubCategoryDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.SubCategory;
import com.anjali.util.HibernateUtil;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author shekharkumar
 */
public class SubCategoryDAOImpl implements SubCategoryDAO {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public SubCategory create(SubCategory subCategory) throws DBException {
        Session session = getSession();
        session.beginTransaction();
        session.save(subCategory.getSubCategoryDetails());
        session.save(subCategory);
        session.getTransaction().commit();
        return subCategory;
    }

    public SubCategory update(SubCategory subCategory) throws DBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SubCategory remove(Long id) throws DBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SubCategory find(Long id) throws DBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
	public List<SubCategory> findAll() throws DBException {
        Query query = getSession().createQuery("Select s from SubCategory s");
        return query.list();
    }

}
