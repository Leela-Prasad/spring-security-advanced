package com.virtualpairprogrammers.webcontrollers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.virtualpairprogrammers.services.security.InMemorySocialUserDetailsService;

@Controller
public class SocialAuthController {

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	@Autowired
	private UsersConnectionRepository connectionRepository;
	
	private ProviderSignInUtils signInUtils;
	
	@Autowired
	private InMemorySocialUserDetailsService userDetailsService;
	
	//This is not done is constructor and is not autowired because 
	//at the time of creation connectionFactoryLocator will be null;
	@PostConstruct
	public void intialise() {
		signInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public ModelAndView confirmUserIsHappyToLinkAccount(WebRequest request) {
		Connection<?> connection = signInUtils.getConnectionFromSession(request);
		
		String provider = connection.getKey().getProviderId();
		return new ModelAndView("/link-with-provider.jsp","provider", provider);
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public ModelAndView registerUserFromFacebook(WebRequest request) {
		/* we can get id from SocialAuthenticationServiceLocator which is wired by 
		 * facebook:config here we can do below operation userProfile.getId()
		 * but this will give you only the id, if we integrate with multiple social media apps
		 * there are chances that this id may collide, so it is better to use connection.getKey()
		 * which will prefix id with social media name.
		 */
		Connection<?> connection = signInUtils.getConnectionFromSession(request);
		String id = connection.getKey().toString();
		userDetailsService.addUser(id);
		
		//After Adding it will not automatically signin because most of the times 
		//we need to activate the account using a link sent to email so to allow this 
		//customisation framework will not login automatically.
		signInUtils.doPostSignUp(id, request);
		
		return new ModelAndView("redirect:/website/all-customers.html");
	}
}
