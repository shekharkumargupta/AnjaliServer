/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import com.anjali.dao.CategoryDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.CategoryDAOImpl;
import com.anjali.domain.Category;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ramesh
 */
@Path(value = "category")
public class CategoryResource {
    
    private final CategoryDAO categoryDAO;
    
    public CategoryResource(){
        categoryDAO = new CategoryDAOImpl();
    }

    @GET
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> findAll() {
        List<Category> categoryList = null;
        try {
            categoryList = categoryDAO.findAll();
        } catch (DBException ex) {
            Logger.getLogger(CategoryResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryList;
    }
    
    @PUT
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category create(Category category){
        try {
            categoryDAO.create(category);
        } catch (DBException ex) {
            Logger.getLogger(CategoryResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }
}
