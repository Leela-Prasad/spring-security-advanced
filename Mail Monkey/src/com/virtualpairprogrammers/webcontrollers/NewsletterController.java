package com.virtualpairprogrammers.webcontrollers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtualpairprogrammers.rest.representations.CustomerClientVersion;
import com.virtualpairprogrammers.rest.representations.CustomerCollectionRepresentation;

@Controller
public class NewsletterController 
{	
	@Autowired
	private OAuth2RestTemplate oauthTemplate;
	
	@RequestMapping("/build-newsletter")
	public ModelAndView displayAllCustomersOnWebPage()
	{
		return new ModelAndView("/newsletter.jsp");
	}
	
	@RequestMapping("/importFromCRM.html")
	public ModelAndView muchBetterVersion()
	{
		CustomerCollectionRepresentation customers = oauthTemplate.getForObject("http://localhost:8080/crm/rest/customers",CustomerCollectionRepresentation.class);
		return new ModelAndView("/importedContacts.jsp","customers",customers.getCustomers());	
	}
	
	@RequestMapping(value="/exportToCRM.html", method=RequestMethod.GET)
	public ModelAndView show()
	{
		CustomerClientVersion newCustomer = new CustomerClientVersion();
		newCustomer.setCompanyName("Virtual Pair Programmers");
		newCustomer.setCustomerId("VPP-1001");
		newCustomer.setEmail("richard@capabilitytm.com");
		newCustomer.setTelephone("6323003");
		newCustomer.setNotes("This has been prepopulated, for convenience!");
		return new ModelAndView("/exportForm.jsp", "customer", newCustomer);
	}	

	@RequestMapping(value="/exportToCRM.html", method=RequestMethod.POST)
	public ModelAndView processForm(@Valid CustomerClientVersion customer, Errors result)
	{
		if (result.hasErrors())
		{
			return new ModelAndView("/exportForm.jsp", "customer", customer);
		}
		String url = "http://localhost:8080/crm/rest/customers";
		
		// call the external CRM system and then redirect to show all contacts page.
		oauthTemplate.postForLocation(url, customer);
		return new ModelAndView("redirect:/importFromCRM.html");
	}

}
