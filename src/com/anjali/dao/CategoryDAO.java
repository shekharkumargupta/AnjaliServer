/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Category;

import java.util.List;

/**
 *
 * @author Ramesh
 */
public interface CategoryDAO {
    
    public Category create(Category category) throws DBException;
    public Category update(Category category) throws DBException;
    public Category remove(Long id) throws DBException;
    public Category find(Long id)throws DBException;
    public Category findByName(String categoryName)throws DBException;
    public List<Category> findAll() throws DBException;
}
