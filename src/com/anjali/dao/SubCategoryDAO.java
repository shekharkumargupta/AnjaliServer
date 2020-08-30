/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.SubCategory;

import java.util.List;

/**
 *
 * @author shekharkumar
 */
public interface SubCategoryDAO {
    
    public SubCategory create(SubCategory subCategory) throws DBException;
    public SubCategory update(SubCategory subCategory) throws DBException;
    public SubCategory remove(Long id) throws DBException;
    public SubCategory find(Long id)throws DBException;
    public List<SubCategory> findAll() throws DBException;
}
