 package com.virtualpairprogrammers.services.diary;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.virtualpairprogrammers.domain.Action;

/**
 * This interface defines the functionality required in the Diary Management Service.
 *
 * @author Richard Chesterwood
 */
@PreAuthorize("isAuthenticated()")
public interface DiaryManagementService 
{
	/**
	 * Records an action in the diary
	 */
	public void recordAction(Action action);
	
	/**
	 * Gets all actions for a particular user that have not yet been complete
	 */
	//Here this method will be executed only when the login user requests.
	@PreAuthorize("hasRole('ROLE_CRM_USER') and principal.username == #requiredUser")
	public List<Action> getAllIncompleteActions(String requiredUser);
}
