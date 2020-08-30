/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.anjali.domain.Person;
import com.anjali.domain.PersonDetails;

/**
 *
 * @author Ramesh
 */
@Path(value = "test")
public class TestResource {

    @GET
    public String welcome() {
        return "Welcome";
    }

    @GET
    @Path(value = "message/{userName}")
    @Produces("application/json")
    public Person message(
            @PathParam(value = "userName") String userName) {
        Person person = new Person();
        person.setId(1);
        person.setFullName(userName);
        person.setEmail(userName + "@gmail.com");

        PersonDetails details = new PersonDetails();
        details.setId(2);
        details.setAddressLine1("Plot 1, Road 13");
        details.setAddressLine2("Sector - 22, Near by Adobe");
        details.setCity("NOIDA");
        details.setProvince("Uttar Pradesh");
        details.setPrimaryContact("8802782657");
        details.setSecondaryContact("9868351070");

        //EmailServices.sendWelcomeMessage("skdubeys@gmail.com");

        return person;
    }
}
