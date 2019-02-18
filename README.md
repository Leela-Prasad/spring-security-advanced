As of now we secured applications based on url’s, this will be requirement for most of the organisations so that the can secure there web applications and rest endpoints.

But if we run our business service classes in standalone mode then it will run automatically and there is no security required because we didn’t applied security for business tier.

To avoid this we can apply security to our business services using @Secured Annotation, this will not only secure our business tier, but it will secure our applications when the url-patterns in xml file got messed up. so this will act as a second layer of defense to our application. So this is highly recommended to apply security for Business Tier.

/*This @Secured Annotation provides business tier security 
  and if users want to use this service then they should have 
  role defined in this annotation. If you define @Secured annotation at 
  the class level then it will be applied to all methods in the class, 
  we can even define this annotation on a method as well.
  */
@Secured({ "ROLE_CRM_USER", "ROLE_CRM_ADMIN" })
public interface CustomerManagementService 
{

}

Annotating a class will not enable the security we have to tell spring to scan for this @Secured annotation via config file.

For XML based application 
<global-method-security secured-annotations="enabled"/>

For Java Config 
@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

}

In the client we have to add below code before calling business service method.
UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("rac", "secret");
SecurityContext securityContext = new SecurityContextImpl();
securityContext.setAuthentication(authToken);
SecurityContextHolder.setContext(securityContext);

We can also do above type of security using AOP
1. I am removing @Secured annotation on CustomerService class
2. Add point cut in global-method-security tag
<security:global-method-security secured-annotations="enabled">
	<security:protect-pointcut access="ROLE_CRM_USER" expression="execution(* com.virtualpairprogrammers.services..*.*(..))"/>
</security:global-method-security>


Now all the classes and methods under com.virtualpairprogrammers.services package will be protected and they should have ROLE_CRM_USER Role to access those resources.
 
                                       All  Any arguments(including zero arguments)
                                   Classes
                                       |    |
* com.virtualpairprogrammers.services..*.*(..))
|                                    |   |
any return type                      All All
                                     Sub Methods
                                     packages


@RolesAllowed:
we can also use @RolesAllowed(“ROLE_CRM_USER”) Annotation instead of Secured annotation as this is jsr250 annotation, spring will recognise and provide security support for this annotation.

To enable this annotation the config line will be 
<global-method-security jsr250-annotations=“enabled"/>

Pre-Post Authorization:
Pre-Post Authorization is also used to secure business tier layer and is similar to @Secured Annotation, but we can execute expression using Pre and Post Authorization.
We can execute rule before and after business service method invocation.

configuration:
<security:global-method-security pre-post-annotations="enabled" />

@PreAuthorize("hasRole('ROLE_CRM_USER')")
public interface CustomerManagementService 
{
…

//This method results will be sent back to client only when list size is < 100
@PostAuthorize("returnObject.size() < 100")
public List<Customer> getAllCustomers();

…

}


@PreAuthorize("isAuthenticated()")
public interface DiaryManagementService 
{

//Here this method will be executed only when the login user requests.
@PreAuthorize("hasRole('ROLE_CRM_USER') and principal.username == #requiredUser")
public List<Action> getAllIncompleteActions(String requiredUser);
}


LDAP(Lightweight Directory Access Protocol)
<security:authentication-manager>
	<security:ldap-authentication-provider user-search-filter="(uid={0})"
							user-search-base="ou=Users"
							group-search-base="ou=Groups"
						group-search-filter="(uniquemember={0})"
						server-ref="ldapServer">
	</security:ldap-authentication-provider>
</security:authentication-manager>

<!-- This is for Running LDAP Server in Embedded mode -->
<security:ldap-server id="ldapServer" ldif="classpath:ldap-repository.ldif" root="dc=virtualpairprogrammers,dc=com" />

dc - domain component
dn - distinguised name


CAS(Central Authentication System):
Generally Applications will redirect to CAS(Predefined SSO Webapp) for authentication, once user enter credentials then all application can ask CAS whether user is authenticated.

We can also write this SSO Webapp but there are lot of SSO Webapp available in market so we can reuse.


CAS - LDAP Integration:
There are 2 approaches for LDAP Integration

Approach1: 
In this approach we will get authentication done by CAS System.
we will ask LDAP for Roles(i.e., authorisation)

Disadvantage of this approach is all client need to configure the query LDAP server for Role information. so in future if LDAP Server is changed then all the clients need modification to point to new LDAP Server.

Approach2:
In this approach we will get authentication and role information form CAS Server.

Advantage:
Clients doesn’t know about the LDAP Server that exist behind CAS.

Disadvantage:
One the field in the LDAP File should be used for Role like description.
