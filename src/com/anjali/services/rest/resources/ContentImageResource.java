/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import static com.anjali.util.IOUtil.getBytes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.anjali.dao.ContentImageDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.ContentImageDAOImpl;
import com.anjali.domain.ContentImage;
import com.anjali.util.IOUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * 
 * @author shekharkumar
 */
@Path(value = "contentImage")
public class ContentImageResource {

    private final ContentImageDAO contentImageDAO;

    public ContentImageResource() {
	contentImageDAO = new ContentImageDAOImpl();
    }

    @GET
    @Path(value = "findByImageId/{id}")
    @Produces({ "image/jpg", "image/png" })
    public Response findByIdeaId(@PathParam(value = "id") final Long id) throws IOException {

	return Response.ok().entity(new StreamingOutput() {
	    @Override
	    public void write(OutputStream output) throws IOException,
		    WebApplicationException {
		ContentImage image = new ContentImage();
		try {
		    image = contentImageDAO.findById(id);
		} catch (DBException ex) {
		    Logger.getLogger(ContentImageResource.class.getName()).log(
			    Level.SEVERE, null, ex);
		}
		byte[] content = image.getImage();
		output.write(content);
		output.flush();
	    }
	}).build();
    }

    @POST
    @Path("uploadImage/{contentId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadImage(
	    @DefaultValue("true") @FormDataParam("enabled") boolean enabled,
	    @FormDataParam("file") InputStream uploadedInputStream,
	    @FormDataParam("file") FormDataContentDisposition fileDetail,
	    @FormDataParam("description") String description,
	    @PathParam("contentId") long contentId) {

	System.out.println("File Name: " + fileDetail.getFileName());
	System.out.println("Content Id: " + contentId);
	System.out.println("Description: " + description);
	System.out.println("File Size: " + fileDetail.getSize());
	try {
	    ContentImage image = new ContentImage();
	    image.setDescription(description);
	    image.setContentId(contentId);
	    image.setImage(getBytes(uploadedInputStream, fileDetail.getSize()));
	    contentImageDAO.create(image);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
    }

    @POST
    @Path("changeImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void changeImage(
	    @DefaultValue("true") @FormDataParam("enabled") boolean enabled,
	    @FormDataParam("file") InputStream uploadedInputStream,
	    @FormDataParam("file") FormDataContentDisposition fileDetail,
	    @FormDataParam("ideaId") long ideaId) {
	System.out.println("changeImage: " + ideaId);
	try {
	    ContentImage image = new ContentImage();
	    image.setImage(IOUtil.getBytes(uploadedInputStream,
		    fileDetail.getSize()));
	    System.out.println("uploadImage: " + image);
	    contentImageDAO.update(image);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
    }
}
