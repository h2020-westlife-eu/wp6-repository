package org.cirmmp.spring.configuration;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

public class XMockServletContext implements ServletContext {

	private Collection<EventListener> listeners = new HashSet<EventListener>();
	private Map<String, String> initParameters = new HashMap<String, String>();
	private Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public Dynamic addFilter(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dynamic addFilter(String arg0, Filter arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends EventListener> void addListener(T arg0) {
		this.listeners.add(arg0);
		// System.out.println(arg0.getClass().getName());
	}

	@Override
	public void addListener(Class<? extends EventListener> arg0) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1) {
		System.out.println(this.getClass().getName()+" registered: "+arg0); // TODO log
		javax.servlet.ServletRegistration.Dynamic d = new MockDynamic(arg0, arg1);
		return d;
	}

	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void declareRoles(String... arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getAttribute(String arg0) {
		return this.attributes.get(arg0);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration( this.attributes .keySet() );
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletContext getContext(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		return "/";
	}

	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		// TODO Auto-generated method stub
		return Collections.EMPTY_SET;
	}

	@Override
	public int getEffectiveMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEffectiveMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return Collections.EMPTY_SET;
	}

	@Override
	public FilterRegistration getFilterRegistration(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return Collections.EMPTY_MAP;
	}

	@Override
	public String getInitParameter(String arg0) {
		return this.initParameters.get(arg0);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return java.util.Collections.enumeration( this.initParameters.keySet() ) ;
	}

	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMimeType(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getResource(String arg0) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getResourcePaths(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servlet getServlet(String arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletContextName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getServletNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletRegistration getServletRegistration(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<Servlet> getServlets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVirtualServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void log(String arg0) {
		System.out.println(arg0); // TODO log
	}

	@Override
	public void log(Exception arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void log(String arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String arg0) {
		this.attributes.remove(arg0);
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		System.out.println(this.getClass().getName()+" attribute: "+arg0+"="+arg1);
		this.attributes.put(arg0, arg1);
	}

	@Override
	public boolean setInitParameter(String arg0, String arg1) {
		System.out.println(this.getClass().getName()+" init parameter: "+arg0+"="+arg1);
		boolean ret = this.initParameters.containsKey(arg0);
		this.initParameters.put(arg0, arg1);
		return ret;
	}

	@Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent event) {
		//System.out.println(this.listeners.size());
		for (EventListener listener: this.listeners) {
			System.out.println(listener.getClass().getName());
			if (listener instanceof ServletContextListener) {
				// this initializes the WebApplicationContext
				((ServletContextListener)listener).contextInitialized(event);
			}
		}
		// TODO how does the Web Context get initialized? This seems to be a separate event in Spring
		// https://stackoverflow.com/questions/11453530/applicationcontext-not-finding-controllers-for-servlet-context
		// https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc
	}

}
