/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import com.anjali.constants.SessionConstants;
import com.anjali.dao.EnquiryDAO;
import com.anjali.dao.ContentDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.EnquiryDAOImpl;
import com.anjali.dao.impl.ContentDAOImpl;
import com.anjali.domain.Enquiry;
import com.anjali.domain.Content;
import com.anjali.domain.Login;
import com.anjali.services.EmailServices;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author shekharkumar
 */
@Path(value = "enquiry")
public class EnquiryResource {

    private final EnquiryDAO enquiryDAO;
    private final ContentDAO contentDAO;

    public EnquiryResource() {
        enquiryDAO = new EnquiryDAOImpl();
        contentDAO = new ContentDAOImpl();
    }

    @GET
    @Path("findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Enquiry findById(@PathParam(value = "id") Long id) {
        Enquiry enquiry = null;
        try {
            enquiry = enquiryDAO.find(id);
        } catch (DBException ex) {
            Logger.getLogger(EnquiryResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enquiry;
    }

    @GET
    @Path("findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Enquiry> findAll() {
        List<Enquiry> enquiryProxyList = new ArrayList<Enquiry>();
        List<Enquiry> enquiryList = null;
        try {
            enquiryList = enquiryDAO.findAll();
        } catch (DBException ex) {
            Logger.getLogger(EnquiryResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Enquiry enquiry : enquiryList) {
            enquiryProxyList.add(enquiry);
        }
        return enquiryProxyList;
    }

    @GET
    @Path("findAllByLoginId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Enquiry> findAllByLoginId(@PathParam(value = "id") long id) {
        List<Enquiry> enquiryProxyList = new ArrayList<Enquiry>();
        List<Enquiry> enquiryList = null;
        try {
            enquiryList = enquiryDAO.findAllByLoginId(id);
        } catch (DBException ex) {
            Logger.getLogger(EnquiryResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Enquiry enquiry : enquiryList) {
            enquiryProxyList.add(enquiry);
        }
        return enquiryProxyList;
    }

    @POST
    @Path("create/{ideaId}/{emailId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Enquiry create(Enquiry enquiry,
            @PathParam(value = "ideaId") long ideaId,
            @PathParam(value = "emailId") String emailId,
            @Context HttpServletRequest request) throws DBException {
        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute(SessionConstants.LOGIN);
        Content content = contentDAO.find(ideaId);
        try {
            enquiry.setContent(content);
            enquiry.setEnquiredBy(login);
            enquiryDAO.create(enquiry);
            
            EmailServices.sendEnquiryAcceptanceNotification(emailId, content.getHeading());
        } catch (DBException ex) {
            Logger.getLogger(EnquiryResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enquiry;
    }

    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Enquiry update(Enquiry enquiry) throws DBException {
        try {
            enquiryDAO.create(enquiry);
        } catch (DBException ex) {
            Logger.getLogger(EnquiryResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enquiry;
    }
}
