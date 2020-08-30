package com.anjali.services.rest.resources;

import static com.anjali.util.IOUtil.getBytes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.anjali.dao.LoginDAO;
import com.anjali.dao.ProfileImageDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.LoginDAOImpl;
import com.anjali.dao.impl.ProfileImageDAOImpl;
import com.anjali.domain.Login;
import com.anjali.domain.Person;
import com.anjali.domain.ProfileImage;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path(value = "profileImage")
public class ProfileImageResource {

    private final LoginDAO loginDAO;
    private final ProfileImageDAO profileImageDAO;

    public ProfileImageResource() {
	loginDAO = new LoginDAOImpl();
	profileImageDAO = new ProfileImageDAOImpl();
    }

    @POST
    @Path("uploadProfilePic/{loginId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadImage(@PathParam(value = "loginId") String loginId,
	    @DefaultValue("true") @FormDataParam("enabled") boolean enabled,
	    @FormDataParam("file") InputStream uploadedInputStream,
	    @FormDataParam("file") FormDataContentDisposition fileDetail,
	    @FormDataParam("ideaId") long contentId) {

	System.out.println("Login Id: " + loginId);
	System.out.println("File Name: " + fileDetail.getFileName());

	try {

	    Login login = loginDAO.findByLoginId(loginId);
	    Person person = login.getPerson();
	    /*
	     * ProfileImage image = new ProfileImage(); image.setPerson(person);
	     * image.setImage(IOUtil.getBytes(uploadedInputStream,
	     * fileDetail.getSize())); profileImageDAO.create(image);
	     */

	    ProfileImage foundImage = profileImageDAO.findByLoginId(loginId);
	    if (foundImage != null) {
		foundImage.setImage(getBytes(uploadedInputStream,
			fileDetail.getSize()));
		profileImageDAO.update(foundImage);
	    } else {
		ProfileImage image = new ProfileImage();
		image.setPerson(person);
		image.setImage(getBytes(uploadedInputStream,
			fileDetail.getSize()));
		profileImageDAO.create(image);
	    }
	} catch (DBException e) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, e);
	}
    }

    @GET
    @Path(value = "findByLoginId/{loginId}")
    @Produces({ "image/jpg", "image/png" })
    public Response findByIdeaId(
	    @PathParam(value = "loginId") final String loginId,
	    @Context HttpServletResponse response) throws IOException {

	return Response.ok().entity(new StreamingOutput() {
	    @Override
	    public void write(OutputStream output) throws IOException,
		    WebApplicationException {
		ProfileImage image = new ProfileImage();
		try {
		    image = profileImageDAO.findByLoginId(loginId);
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
}
