package com.anjali.services.wsserver;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.anjali.domain.Message;
import com.google.gson.Gson;

@ServerEndpoint("/chatServerEndpoint/{userId}")
public class ChatServerEndpoint {

    private static Set<Session> onlineUsers = Collections
	    .synchronizedSet(new HashSet<Session>());
    private static Map<String, Session> onlineUsersMap = new HashMap<String, Session>();

    @OnOpen
    public void handleOpen(@PathParam(value = "userId") String userId,
	    Session userSession) {
	userSession.getUserProperties().put("username", userId);
	onlineUsers.add(userSession);
	onlineUsersMap.put(userId, userSession);

	Set<String> users = onlineUsersMap.keySet();
	System.out.println("Connected users: ");
	for (String user : users) {
	    System.out.println(user);
	}

    }

    @OnMessage
    public void handleMessage(String messageString, Session userSession)
	    throws IOException {
	System.out.println("MessageString: " + messageString);

	// messageString.replace("\"", "&quot;");
	Gson gson = new Gson();
	Message message = gson.fromJson(messageString, Message.class);

	if (message != null) {
	    // String messageTo = message.substring(0, message.indexOf("#"));
	    Session session = onlineUsersMap.get(message.getTo().getLoginId());
	    System.out.println("Message To: "
		    + session.getUserProperties().get("username"));
	    // session.getBasicRemote().sendText(buildJsonData(messageTo,
	    // message));
	    session.getBasicRemote().sendText(messageString);
	}
    }

    @OnClose
    public void handleClose(Session userSession) {
	onlineUsers.remove(userSession);
    }

    public static Set<String> getOnlineUsers() {
	Set<String> users = onlineUsersMap.keySet();
	return users;
    }

    public static void main(String args[]) throws JsonGenerationException,
	    JsonMappingException, IOException {
	/*
	 * String jsonString = buildJsonData("shekhar@gmail.com",
	 * "How are you?"); System.out.println(jsonString);
	 */
    }
}
