package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.User;
import exceptions.CredentialsException;
import services.UserService;

@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/UserService")
	private UserService userService;

	public UserRegistration() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usernamer = null;
		String pwdr = null;
		String typer = null;
		String mailr = null;
		
		
		try {
			usernamer = StringEscapeUtils.escapeJava(request.getParameter("usernamer"));
			pwdr = StringEscapeUtils.escapeJava(request.getParameter("pwdr"));
			typer = StringEscapeUtils.escapeJava(request.getParameter("typer"));
			mailr = StringEscapeUtils.escapeJava(request.getParameter("mailr"));
			
			if (usernamer == null || pwdr == null || typer == null || mailr == null || 
					usernamer.isEmpty() || pwdr.isEmpty() || typer.isEmpty() || mailr.isEmpty() ) 
			{
				throw new Exception("Missing or empty registration values");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty registration values");
			return;
		}

		User user;
		try {
			user = userService.checkCredentials(usernamer, pwdr);
		} catch (CredentialsException | NonUniqueResultException e ) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check registration values");
			return;
		}

		String path;
		if (user == null) {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsg", "Incorrect username or password");
			path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else 
		{
			request.getSession().setAttribute("user", user);
			if(user.getUsertype().getUsertype().equals("Customer"))
			{
				path = getServletContext().getContextPath() + "/HomeCustomer";
				response.sendRedirect(path);
			}
			else if(user.getUsertype().getUsertype().equals("Employee"))
			{
				path = getServletContext().getContextPath() + "/HomeEmployee";
				response.sendRedirect(path);
			}
		}

	}

	public void destroy() {
		
	}
}
