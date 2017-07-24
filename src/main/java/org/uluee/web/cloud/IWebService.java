package org.uluee.web.cloud;

import java.util.List;

import org.uluee.web.cloud.model.User;

public interface IWebService {
	
	public User login(String username, String password);
	public List<String> getGoogleAutocomplete(String match);
}
