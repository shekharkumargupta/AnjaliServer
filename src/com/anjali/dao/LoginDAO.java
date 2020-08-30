/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Login;

import java.util.List;

/**
 *
 * @author Ramesh
 */
public interface LoginDAO {
    
    public Login create(Login login) throws DBException;
    public Login update(Login login) throws DBException;
    public Login remove(Long id) throws DBException;
    
    
    public List<Login> findAll() throws DBException;
    public List<Login> friendList(String loginId) throws DBException;
    public List<Login> findAllByProfession(String professionName) throws DBException;
    public List<Login> search(String searchString) throws DBException;
    
    public Login find(Long id) throws DBException;
    public Login findByLoginId(String loginId) throws DBException;
    
    public Login verifyUser(String loginId, String password) throws DBException;
}
