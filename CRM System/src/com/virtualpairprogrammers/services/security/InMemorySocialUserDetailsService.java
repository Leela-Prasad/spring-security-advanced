package com.virtualpairprogrammers.services.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class InMemorySocialUserDetailsService implements SocialUserDetailsService {

	private Map<String,SocialUser> userList = new HashMap<String,SocialUser>();
	
	@Override
	public SocialUserDetails loadUserByUserId(String id) throws UsernameNotFoundException {
		SocialUser user = userList.get(id);
		if(user == null)
			throw new UsernameNotFoundException("Socail User id : " + id + "not present");
		return user;
	}
	
	public void addUser(String id) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CRM_USER"));
		SocialUser user = new SocialUser(id, "not needed", authorities);
		userList.put(id, user);
		
		//To automatically signin we have to update the credentials in SecurityContexHolder.
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(id, null,authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		
		System.out.println("User is added with id : " + id);
	}

}
