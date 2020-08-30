/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.ContentImage;

import java.util.List;

/**
 *
 * @author shekharkumar
 */
public interface ContentImageDAO {
    
    public ContentImage create(ContentImage image)throws DBException;
    public ContentImage update(ContentImage image)throws DBException;
    public ContentImage findById(long id) throws DBException;
    public ContentImage findByContentId(long id) throws DBException;
    public List<ContentImage> findAll()throws DBException;
}
