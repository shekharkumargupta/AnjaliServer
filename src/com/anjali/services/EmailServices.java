/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author shekharkumar
 */
public class EmailServices {

    private static final String USER_NAME = "shekharkumargupta@gmail.com";
    private static final String PASSWORD = "magic@30";

    public static void sendMail(String subject, String htmlMessageString, String to) {
        Properties props = System.getProperties();

        props.put(
                "mail.smtp.starttls.enable", true); // added this line
        props.put(
                "mail.smtp.host", "smtp.gmail.com");
        props.put(
                "mail.smtp.user", USER_NAME);
        props.put(
                "mail.smtp.password", PASSWORD);
        props.put(
                "mail.smtp.port", "587");
        props.put(
                "mail.smtp.auth", true);

        Session session = Session.getInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        System.out.println(
                "Port: " + session.getProperty("mail.smtp.port"));

        try {
            InternetAddress from = new InternetAddress(USER_NAME);
            message.setSubject(subject);
            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            Multipart multipart = new MimeMultipart("alternative");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("some text to send");

            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlMessageString, "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", USER_NAME, PASSWORD);
            System.out.println("Transport: " + transport.toString());
            transport.sendMessage(message, message.getAllRecipients());

        } catch (AddressException e) {
        } catch (MessagingException e) {
        }
    }

    public static void sendWelcomeMessage(String to) {
        sendMail("Welcome to Shethjee.com", UtilServices.welcomeMessage(to), to);
    }

    public static void sendEnquiryAcceptanceNotification(String to,
            String ideaName) {
        sendMail("Shethjee.com | Enquiry received.", 
                UtilServices.EnquiryAcceptanceNotification(to, ideaName), to);
    }
}
