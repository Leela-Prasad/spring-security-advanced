package com.virtualpairprogrammers.client;

import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LDAPServer {

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
		
		try
		{
			System.out.println("LDAP Service Started. Press enter to stop it.");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();

		}
		finally
		{
			container.close();
			System.out.println("System stopped");
		}
	}

}
