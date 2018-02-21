package org.cirmmp.spring.configuration;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.ServletSecurityElement;

public class MockDynamic implements Dynamic {

	public MockDynamic(String arg0, Servlet arg1) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<String> addMapping(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRunAsRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInitParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getInitParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setInitParameter(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> setInitParameters(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAsyncSupported(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoadOnStartup(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMultipartConfig(MultipartConfigElement arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRunAsRole(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> setServletSecurity(ServletSecurityElement arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
