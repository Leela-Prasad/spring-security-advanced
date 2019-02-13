package com.virtualpairprogrammers.webcontrollers;

import java.util.List;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FacebookController {

	//Connection Factory with Client Credentials(client id, client secret
	private FacebookConnectionFactory cf = new FacebookConnectionFactory("795947030790092", "fc8bed8c8009454068197169d91faa2a");
	
	@RequestMapping("/facebook/facebook.html")
	public String startFacebookProcess() {
		OAuth2Operations operations = cf.getOAuthOperations();
		
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri("http://localhost:8080/crm/facebook/returnFromFacebook.html");
		params.setScope("email,public_profile,user_friends,user_posts");
		
		//Leg1 to get the Authorization code
		String url = operations.buildAuthorizeUrl(params);
		System.out.println("URL :" + url);
		return "redirect:" + url;
	}
	
	@RequestMapping("/facebook/returnFromFacebook.html")
	public ModelAndView getDataFromFacebook(@RequestParam("code") String authorizationCode) {
		OAuth2Operations operations = cf.getOAuthOperations();
		
		//Leg2 to get Access Token
		AccessGrant accessGrant = operations.exchangeForAccess(authorizationCode, "http://localhost:8080/crm/facebook/returnFromFacebook.html", null);
		
		//Leg3
		Connection<Facebook> connection = cf.createConnection(accessGrant);
		
		//String email = connection.fetchUserProfile().getEmail();
		String name = connection.getDisplayName();
		//String username = connection.fetchUserProfile().getUsername();
		
		System.out.println("Display name :" + name);
		
		Facebook facebook = connection.getApi();
		String [] fields = { "id", "email",  "first_name", "last_name" };
		User userProfile = facebook.fetchObject("me", User.class, fields);
		System.out.println("Profile :" + userProfile);
		System.out.println(userProfile.getBirthday() + userProfile.getEmail() + userProfile.getGender() + userProfile.getId() + userProfile.getRelationshipStatus());
		
		List<Post> posts = facebook.feedOperations().getFeed();
		for (Post post : posts) {
			System.out.println("Post : " + post.getMessage());
		}
		
		List<User> myFriends = facebook.friendOperations().getFriendProfiles();
		for (User user : myFriends) {
			System.out.println("Friend :" + user.getId());
		}
		
		return null;
	}
}
