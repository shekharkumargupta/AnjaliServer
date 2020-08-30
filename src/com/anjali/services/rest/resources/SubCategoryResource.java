/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anjali.dao.SubCategoryDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.SubCategoryDAOImpl;
import com.anjali.domain.SubCategory;

/**
 *
 * @author shekharkumar
 */
@Path(value = "subCategory")
public class SubCategoryResource {
    
    private final SubCategoryDAO subCategoryDAO;
    
    public SubCategoryResource(){
        subCategoryDAO = new SubCategoryDAOImpl();
    }

    @GET
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubCategory> findAll() {
        List<SubCategory> subCategoryList = null;
        try {
            subCategoryList = subCategoryDAO.findAll();
        } catch (DBException ex) {
            Logger.getLogger(SubCategoryResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subCategoryList;
    }
    
    
    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SubCategory create(SubCategory subCategory){
        try {
            subCategoryDAO.create(subCategory);
        } catch (DBException ex) {
            Logger.getLogger(SubCategoryResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subCategory;
    }
}
