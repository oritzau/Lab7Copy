package edu.skidmore.cs276.lab07.webapps.web;

import java.util.Enumeration;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

import org.apache.log4j.Logger;

import edu.skidmore.cs276.lab07.persistence.DatabaseConnection;
import edu.skidmore.cs276.lab07.persistence.ORM;

/**
 * <p>
 * Title: Controller Servlet for the provider search application
 * </p>
 *
 * <p>
 * Description: Servlet for searching and displaying information about medical
 * providers
 * </p>
 *
 * @author David Read
 * @version 01.00.00
 */
public class ControllerServlet extends HttpServlet {
	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501L;

	/**
	 * Servlet version
	 */
	private static final String VERSION = "01.00.00";

	/**
	 * Logger Instance
	 */
	private static Logger LOGGER = Logger.getLogger(ControllerServlet.class);

	/**
	 * Servlet config init parameter name for the RDB driver class
	 */
	private final static String SERVLET_CONFIG_PARAM_RDB_DRIVER_CLASS = "RdbDriverClass";

	/**
	 * Servlet config init parameter name for the RDB connection URL
	 */
	private final static String SERVLET_CONFIG_PARAM_RDB_URL = "RdbUrl";

	/**
	 * Servlet config init parameter name for the RDB connection user id
	 */
	private final static String SERVLET_CONFIG_PARAM_RDB_USER_ID = "RdbUserId";

	/**
	 * Servlet config init parameter name for the RDB connection password
	 */
	private final static String SERVLET_CONFIG_PARAM_RDB_PASSWORD = "RdbPassword";

	/**
	 * Request parameter for the provider id
	 */
	public final static String REQUEST_PROVIDER_ID = "providerId";

	/**
	 * Request parameter for the provider last name
	 */
	public final static String REQUEST_PROVIDER_LNAME = "providerLastName";

	/**
	 * Request parameter for the provider first name
	 */
	public final static String REQUEST_PROVIDER_FNAME = "providerFirstName";

	/**
	 * Request parameter for the provider speciality
	 */
	public final static String REQUEST_PROVIDER_SPECIALTY = "providerSpeciality";

	/**
	 * Request attribute specialities
	 */
	public final static String REQUEST_ATTRIB_PROVIDER_SPECIALITIES = "requestAttribProviderSpecialities";

	/**
	 * Request attribute for the provider list
	 */
	public final static String REQUEST_ATTRIB_PROVIDER_LIST = "requestAttribProviderList";

	/**
	 * Request attribute for the provider details
	 */
	public final static String REQUEST_ATTRIB_PROVIDER_DETAILS = "requestAttribProviderDetails";

	/**
	 * JSP for the search page
	 */
	public final static String JSP_SEARCH_FORM = "/WEB-INF/hiddenjsp/search_criteria.jsp";

	/**
	 * JSP for the provider list page
	 */
	public final static String JSP_PROVIDER_LIST_VIEW = "/WEB-INF/hiddenjsp/provider_list.jsp";

	/**
	 * JSP for the provider details page
	 */
	public final static String JSP_PROVIDER_DETAILS = "/WEB-INF/hiddenjsp/provider_details.jsp";

	/**
	 * Called by container when servlet instance is created. Logs the current
	 * version of the servlet and sets up the database connection.
	 *
	 * @param config The servlet configuration
	 */
	public void init(ServletConfig config) {
		LOGGER.warn("Servlet init.  Version: " + VERSION);
		setupDatabaseConnection(config);
	}

	/**
	 * Called by the container when the servlet instance is being removed. Cleanup
	 * the database connection.
	 */
	public void destroy() {
		DatabaseConnection.instance().closeDatabaseConnection();
	}

	/**
	 * Setup the database connection using the configuration information in the
	 * deployment descriptor (web.xml)
	 * 
	 * @param config The servlet's config instance
	 */
	private void setupDatabaseConnection(ServletConfig config) {
		try {
			DatabaseConnection.instance().setDatabaseProperties(
					config.getInitParameter(SERVLET_CONFIG_PARAM_RDB_DRIVER_CLASS),
					config.getInitParameter(SERVLET_CONFIG_PARAM_RDB_URL),
					config.getInitParameter(SERVLET_CONFIG_PARAM_RDB_USER_ID),
					config.getInitParameter(SERVLET_CONFIG_PARAM_RDB_PASSWORD));
		} catch (Throwable throwable) {
			LOGGER.fatal("Unable to setup the database", throwable);
			throw new IllegalStateException("Unable to setup the database connection", throwable);
		}
	}

	/**
	 * The constructor - no operations carried out here
	 */
	public ControllerServlet() {
	}

	/**
	 * Uses the controller method to process the request.
	 * 
	 * @see #controller
	 *
	 * @param req  The request
	 * @param resp The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		controller(req, resp);
	}

	/**
	 * Uses the controller method to process the request.
	 *
	 * @see #controller
	 *
	 * @param req  The request
	 * @param resp The response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		controller(req, resp);
	}

	/**
	 * The controlling method for the servlet. Manages the processing of the
	 * request.
	 * 
	 * @param req  The request
	 * @param resp The response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void controller(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jspPath;
		String providerId;
		String providerLastName;
		String providerFirstName;
		String providerSpeciality;

		LOGGER.info("Request method: " + req.getMethod());

		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			LOGGER.info("Received param: " + paramName + " with value " + req.getParameter(paramName));
		}

		providerId = req.getParameter(REQUEST_PROVIDER_ID);
		providerLastName = req.getParameter(REQUEST_PROVIDER_LNAME);
		providerFirstName = req.getParameter(REQUEST_PROVIDER_FNAME);
		providerSpeciality = req.getParameter(REQUEST_PROVIDER_SPECIALTY);

		/* Check what the request is trying to do */
		if (providerId != null) {
			/* Request for provider details */
			req.setAttribute(REQUEST_ATTRIB_PROVIDER_DETAILS, ORM.instance().getProviderDetails(providerId));
			jspPath = JSP_PROVIDER_DETAILS;
		} else if (providerSpeciality != null) {
			/* Request to search for a provider by speciality and optional name */
			req.setAttribute(REQUEST_ATTRIB_PROVIDER_LIST,
					ORM.instance().getProviderList(providerFirstName, providerLastName, providerSpeciality));
			jspPath = JSP_PROVIDER_LIST_VIEW;
		} else {
			/* Request for the initial search form */
			req.setAttribute(REQUEST_ATTRIB_PROVIDER_SPECIALITIES, ORM.instance().getSpecialities());
			jspPath = JSP_SEARCH_FORM;
		}

		LOGGER.info("Resulting JSP: " + jspPath);
		RequestDispatcher dispatch = req.getRequestDispatcher(jspPath);
		dispatch.forward(req, resp);
	}
}
