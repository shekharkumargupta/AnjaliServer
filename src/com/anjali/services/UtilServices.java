/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services;

import com.anjali.constants.ApplicationConstants;

/**
 *
 * @author shekharkumar
 */
public class UtilServices {

    public static String welcomeMessage(String userName) {
        String message = "<h1>Welcome</h1>"
                + "Your Login Id is: " + userName
                + "Please visit to "
                + "<a href=\"" + ApplicationConstants.APP_SEARCH_PAGE_URL + "\">Shethjee.com</a>";

        return message;
    }
    
    public static String EnquiryAcceptanceNotification(String userName, String ideaName) {
        String message = "Dear <b>" + userName + "</b>,"
                + "<br>"
                + "Thank you so much for you interest in business <b>"+ideaName+"</b>."
                + "We have recieved you enquiry. We wil contact you with in next 24 hrs."
                + "Warm Regards"
                + "<br>"
                + "<a href=\"" + ApplicationConstants.APP_SEARCH_PAGE_URL + "\">Shethjee.com</a>";

        return message;
    }
}
