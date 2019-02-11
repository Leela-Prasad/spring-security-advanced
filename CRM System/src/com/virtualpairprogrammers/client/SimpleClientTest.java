package com.virtualpairprogrammers.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.virtualpairprogrammers.domain.Action;
import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.domain.Customer;
import com.virtualpairprogrammers.services.calls.CallHandlingService;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;
import com.virtualpairprogrammers.services.diary.DiaryManagementService;

public class SimpleClientTest {

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application-local.xml");

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("bill", "billssecret");
		SecurityContext securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(authToken);
		SecurityContextHolder.setContext(securityContext);
		
		try
		{
			CustomerManagementService customerService = container.getBean(CustomerManagementService.class);
			CallHandlingService callService = container.getBean(CallHandlingService.class);
			DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

			List<Customer> customers = customerService.getAllCustomers();
			for(Customer customer : customers ) {
				System.out.println(customer);
			}
			
			//diaryService.getAllIncompleteActions("rac");
		}
		finally
		{
			container.close();
		}
	}

}
