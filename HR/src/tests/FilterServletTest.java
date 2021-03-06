package tests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import classes.Account;
import classes.DBManager;
import servlets.FilterServlet;

/**
 * The Class FilterServletTest.
 */
class FilterServletTest extends Mockito {

	/**
	 * This methods Checks if parameters is correct.
	 *
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void checkParameters() throws ServletException, IOException {
		// mock classes
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		HttpSession session = mock(HttpSession.class);
		DBManager manager = mock(DBManager.class);
		Account acc = mock(Account.class);

		// mock servletContext
		final ServletContext servletContext = mock(ServletContext.class);
		FilterServlet serv = new FilterServlet() {
			public ServletContext getServletContext() {
				return servletContext;
			}
		};

		// override methods
		when(servletContext.getAttribute("DBManager")).thenReturn(manager);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("account")).thenReturn(acc);
		when(request.getRequestDispatcher("JSP/VacancyCart.jsp")).thenReturn(dispatcher);

		when(request.getParameter("professions")).thenReturn("professions");
		when(request.getParameter("companies")).thenReturn("companies");
		when(request.getParameter("locations")).thenReturn("locations");
		when(request.getParameter("tags")).thenReturn("tags");
		when(request.getParameter("jobs_type")).thenReturn("jobs_type");
		when(request.getParameter("degree")).thenReturn("degree");

		// call servelt and check if parameters are correct
		serv.doGet(request, response);
		verify(manager).getFilterVacancies("professions", "companies", "locations", "tags", "jobs_type", "degree");
		verify(manager).getCompanies();
		verify(manager).getProfessions();
		verify(manager).getLocations();
		verify(manager).getTags();
		verify(manager).getDegrees();
		verify(dispatcher).forward(request, response);
	}
}
