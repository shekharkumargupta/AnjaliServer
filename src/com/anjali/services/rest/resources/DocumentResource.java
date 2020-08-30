/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.anjali.dao.DocumentDAO;
import com.anjali.dao.ContentDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.DocumentDAOImpl;
import com.anjali.dao.impl.ContentDAOImpl;
import com.anjali.domain.Comment;
import com.anjali.domain.Document;
import com.anjali.domain.Video;
import com.anjali.util.IOUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 *
 * @author Ramesh
 */
@Path(value = "document")
public class DocumentResource {

	private final DocumentDAO documentDAO;
	private final ContentDAO contentDAO;

	public DocumentResource() {
		documentDAO = new DocumentDAOImpl();
		contentDAO = new ContentDAOImpl();
	}

	@GET
	@Path("findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Document findById(@PathParam(value = "id") Long id) {
		return documentDAO.findById(id);
	}

	@GET
	@Path("documents/{ideaId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Document> documentList(@PathParam(value = "ideaId") long ideaId) {
		/*
		 * Content idea = new Content();
		 * 
		 * try { idea = contentDAO.find(ideaId); } catch (DBException ex) {
		 * Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		 * null, ex); } return idea.getDocuments();
		 */
		return null;
	}

	@POST
	@Path("uploadDocument")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadFile(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("ideaId") long ideaId,
			@FormDataParam("aboutDocument") String aboutDocument, @FormDataParam("source") String source) {

		System.out.println("File Name: " + fileDetail.getFileName());
		try {
			Document document = new Document();
			document.setAboutDocument(aboutDocument);
			document.setSource(source);
			document.setFileName(fileDetail.getFileName());
			document.setDocumentType(fileDetail.getType());
			document.setDocumentContent(IOUtil.getBytes(uploadedInputStream, fileDetail.getSize()));
			System.out.println("Uploaded Document: " + document);
			contentDAO.addDocument(ideaId, document);
			System.out.println(document);
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@POST
	@Path("uploadVideo/{ideaId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Video uploadVideo(@PathParam(value = "ideaId") long ideaId, Video video) {
		try {
			contentDAO.addVideo(ideaId, video);
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return video;
	}

	@POST
	@Path("postComment/{ideaId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment postComment(@PathParam(value = "ideaId") long ideaId, Comment comment) {
		try {
			contentDAO.addComment(ideaId, comment);
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return comment;
	}

	@GET
	@Path(value = "download/{id}")
	@Produces({ "application/pdf", "application/msword", "image/jpg", "image/png" })
	public Response downloadById(@PathParam(value = "id") Long id, @Context HttpServletResponse response)
			throws IOException {

		Document document = documentDAO.findById(id);
		byte[] content = document.getDocumentContent();

		String name = document.getId() + "_" + document.getFileName();
		response.setContentLength(content.length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(new ByteArrayInputStream(content));
			output = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[8192];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} catch (IOException e) {
			System.out.println("There are errors in reading/writing image stream " + e.getMessage());
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException ignore) {
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException ignore) {
				}
			}
		}
		return Response.ok().build();
	}

}
