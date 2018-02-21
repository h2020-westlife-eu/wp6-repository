package org.cirmmp.spring.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class MockServletContextEvent extends ServletContextEvent {

	public MockServletContextEvent(ServletContext source) {
		super(source);
	}
	
	

}
