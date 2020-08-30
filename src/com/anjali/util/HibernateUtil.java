/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author Ramesh
 */
public class HibernateUtil {
    
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        AnnotationConfiguration cfg = new AnnotationConfiguration();
        cfg.configure();
        return cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
