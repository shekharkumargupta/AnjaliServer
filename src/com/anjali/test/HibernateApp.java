/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.anjali.constants.ApplicationConstants;
import com.anjali.dao.CategoryDAO;
import com.anjali.dao.ContentDAO;
import com.anjali.dao.LoginDAO;
import com.anjali.dao.MessageDAO;
import com.anjali.dao.ShareBoxDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.CategoryDAOImpl;
import com.anjali.dao.impl.ContentDAOImpl;
import com.anjali.dao.impl.LoginDAOImpl;
import com.anjali.dao.impl.MessageDAOImpl;
import com.anjali.dao.impl.ShareBoxDAOImpl;
import com.anjali.domain.Category;
import com.anjali.domain.Content;
import com.anjali.domain.Login;
import com.anjali.domain.Message;
import com.anjali.domain.Person;
import com.anjali.domain.ShareBox;

/**
 *
 * @author Ramesh
 */
public class HibernateApp {

    private static final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private static final LoginDAO loginDAO = new LoginDAOImpl();
    private static final ContentDAO contentDAO = new ContentDAOImpl();
    //private static final FriendRequestDAO friendRequestDAO = new FriendRequestDAOImpl();	
    private static final MessageDAO messageDAO = new MessageDAOImpl();
    private static final ShareBoxDAO shareBoxDAO = new ShareBoxDAOImpl();

    public static void createCategory(String categoryName) throws DBException {
        Category informationTechnology = new Category();
        informationTechnology.setName(categoryName);
        informationTechnology.setActive(true);
        categoryDAO.create(informationTechnology);
    }

    public static void createLogin(String personName, String emailId, String profession) {
    	Person person = new Person();
    	person.setFullName(personName);
    	person.setEmail(emailId);
    	person.setProfession(profession);
    	
        Login login = new Login();
        login.setPassword(ApplicationConstants.DEFAULT_PASSWORD);
        login.setLoginId(emailId);
        login.setActive(true);
        login.setPerson(person);

        try {
            loginDAO.create(login);
        } catch (DBException ex) {
            Logger.getLogger(HibernateApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void findAllCategories() throws DBException {
        List<Category> categories = categoryDAO.findAll();
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    public static void findAllLogins() throws DBException {
        List<Login> logins = loginDAO.findAll();
        for (Login login : logins) {
            System.out.println(login);
        }
    }
    
    public static void createContent(String heading, String meterial, String categoryName, String loginId) throws DBException{
    	Login createdBy = loginDAO.findByLoginId(loginId);
    	Category category = categoryDAO.findByName(categoryName);
    	
    	Content content = new Content();
    	content.setHeading(heading);
    	content.setMeterial(meterial);
    	content.setCategory(category);
    	content.setCreatedBy(createdBy);
    	
    	contentDAO.create(content);
    }
    
    public static void createAll() throws DBException {
    	//Creating categories
    	createCategory("Health");
    	createCategory("Beauty");
    	createCategory("Kitchen");
    	createCategory("Law");
    	createCategory("Business");
    	createCategory("Jobs");
    	
    	//List out all the categories
    	findAllCategories();
    	
    	//Creating login id
    	createLogin("Shekhar kumar", "shekhar@gmail.com", "J2EE Developer");
    	createLogin("Munish Verma", "manish@gmail.com", "Doctor");
    	createLogin("Anjali Kumari", "anjali@gmail.com", "HouseWife");
    	createLogin("Ravi Sharma", "ravi@gmail.com", "Java Developer");
    	createLogin("Amit Tripathi", "amit@gmail.com", ".Net Developer");
    	createLogin("Bhuwneshwar Kumar", "bk@gmail.com", ".Net Developer");
    	createLogin("Sushil Sharma", "sushil@gmail.com", ".Net Developer");

    	
    	//Creating contents
    	/*
    	createContent("First Heading", "This is my content meterial for the First Heading", "Health", "shekhar@gmail.com");
        createContent("Second Heading", "This is my content meterial for the Second Heading", "Beauty", "shekhar@gmail.com");
        createContent("Third Heading", "This is my content meterial for the Third Heading", "Kitchen", "shekhar@gmail.com");
        createContent("Fourth Heading", "This is my content meterial for the Fourth Heading", "Kitchen", "amit@gmail.com");
        */
    }

    
    public static void sendMessage(String fromLogin, String toLogin, String messageString){
    	try {
			Login from = loginDAO.findByLoginId(fromLogin);
			Login to = loginDAO.findByLoginId(toLogin);
			Message message = new Message();
			message.setFrom(from);
			message.setTo(to);
			message.setMessageContent(messageString);
			message = messageDAO.create(message);
			System.out.println("MessageId: "+message.getId());
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void showAllMessage(){
    	try {
			List<Message> messages = messageDAO.findAll();
			for(Message message: messages){
				System.out.println(message);
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void showAllMessageFrom(String loginId){
    	try {
			List<Message> messages = messageDAO.findAllFrom(loginId);
			for(Message message: messages){
				System.out.println(message);
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void friendList(String loginId) throws DBException{
    	List<Login> friends = loginDAO.friendList("shekhar@gmail.com");
    	for(Login login: friends){
    		System.out.println(login);
    	}
    }
    
    public static void share() throws DBException{
    	Login sharedBy = loginDAO.findByLoginId("shekhar@gmail.com");
    	Login bk = loginDAO.findByLoginId("bk@gmail.com");
    	Login amit = loginDAO.findByLoginId("amit@gmail.com");
    	
    	
    	List<Login> sharedToList = new ArrayList<Login>();
    	sharedToList.add(bk);
    	sharedToList.add(amit);
    	
    	Content content = contentDAO.find(new Long(34));
    	
    	ShareBox shareBox = new ShareBox();
    	shareBox.setContent(content);
    	shareBox.setSharedBy(sharedBy);
    	shareBox.setCreatedAt(Calendar.getInstance().getTime());
    	shareBoxDAO.create(shareBox);
    	
    	System.out.println("ShareBox Id: "+shareBox.getId());
    }
    
    public static void sharedByFriends() throws DBException{
	List<Content> contentList = shareBoxDAO.sharedByFriends("shekhar@gmail.com");
    	System.out.println("total: "+contentList.size());
    	for(Content content: contentList){
    		System.out.println(content);
    	}
    }
    
    public static void sharer(long contentId) throws DBException{
	List<Login> logins = shareBoxDAO.getSharer(contentId);
	for(Login login: logins){
	    System.out.println(login);
	}
    }
    
    public static void main(String args[]) throws DBException {
    	sharer(23);
    }
}
