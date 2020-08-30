/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.constants;

/**
 *
 * @author shekharkumar
 */
public class ApplicationConstants {

	public static final String DEFAULT_PASSWORD = "default";
	public static final String APP_SEARCH_PAGE_URL = "http://166.62.38.63:8080/business/main.html#/ideaSearch";

	public static enum FRIEND_REQUEST_STATUS {
		PENDING, CONFIRMED, CANCELLED;
	};

	public static enum MESSAGE_STATUS {
		NEW, READ, REMOVED
	}
}
