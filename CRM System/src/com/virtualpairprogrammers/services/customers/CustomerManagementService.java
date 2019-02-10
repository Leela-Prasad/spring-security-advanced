package com.virtualpairprogrammers.services.customers;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.virtualpairprogrammers.dataaccess.RecordNotFoundException;
import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.domain.Customer;

/**
 * This interface defines the functionality we want the Customer Management Service
 * to offer. 
 * <p/>
 * Your job is to implement it. In the early sessions you will write a "Mock" to simulate 
 * a Customer Management Service. In later sessions you'll provide a Database backed implementation.
 */
/*This @Secured Annotation provides business tier security 
  and if users want to use this service then they should have 
  role defined in this annotation. If you define @Secured annotation at 
  the class level then it will be applied to all methods in the class, 
  we can even define this annotation on a method as well.
  */
@Secured({ "ROLE_CRM_USER", "ROLE_CRM_ADMIN" })
public interface CustomerManagementService 
{
	/**
	 * Takes a customer domain object and saves it in the database
	 */
	public Customer newCustomer(Customer newCustomer);
	
	/**
	 * The specified customer is updated in the database.
	 * @throws CustomerNotFoundException 
	 */
	public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException;
	
	/**
	 * The specified customer is removed from the database
	 * @throws CustomerNotFoundException 
	 * @throws RecordNotFoundException 
	 */
	public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException;
	
	/**
	 * Finds the customer by Id
	 */
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException;

	/**
	 * Finds customers by their name. Note that in a full system, we'd 
	 * probably offer more sophisticated searching functionality, but for the
	 * practical this will do for now.
	 */
	public List<Customer> findCustomersByName (String name);

	/**
	 * Returns a complete list of the customers in the system.
	 */
	public List<Customer> getAllCustomers();

	/**
	 * For the specified customer, returns the customer object together
	 * will all calls associated with that customer
	 * @throws CustomerNotFoundException 
	 */
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException;
	
	
	/**
	 * Records a call against a particular customer
	 * 
	 * (note that this could be achieved by calling the udpate method, but this is a more
	 * convenient interface)
	 */
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException;
}
