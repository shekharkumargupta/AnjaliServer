/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Enquiry;

import java.util.List;

/**
 *
 * @author shekharkumar
 */
public interface EnquiryDAO {

    public Enquiry create(Enquiry enqury) throws DBException;

    public Enquiry update(Enquiry enqury) throws DBException;

    public Enquiry find(long id) throws DBException;

    public List<Enquiry> findAll() throws DBException;

    public List<Enquiry> findAllByLoginId(long loginId) throws DBException;

}
