package controllers;

import java.io.IOException;

import javax.ejb.EJB;

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


import exceptions.InvalidRegistrationParams;
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
		String pwdr2 = null;
		String typer = null;
		String mailr = null;
		
		String message = null;
		String messagepwd = null;
		String path = "/index.html";
		boolean goToRedirect = false;
			
		try {
			usernamer = StringEscapeUtils.escapeJava(request.getParameter("usernamer"));
			pwdr = StringEscapeUtils.escapeJava(request.getParameter("pwdr"));
			pwdr2 = StringEscapeUtils.escapeJava(request.getParameter("pwdr2"));
			typer = StringEscapeUtils.escapeJava(request.getParameter("typer"));
			mailr = StringEscapeUtils.escapeJava(request.getParameter("mailr"));
			
			if (usernamer == null || pwdr == null || pwdr2 == null || typer == null || mailr == null || 
					usernamer.isEmpty() || pwdr.isEmpty() || pwdr2.isEmpty() || typer.isEmpty() || mailr.isEmpty() ) {
				throw new InvalidRegistrationParams("Missing or empty registration values");
			}
			
			if (!pwdr.equals(pwdr2)) {
				messagepwd = "Passwords do not match";
				goToRedirect = true;
			}
			
		} 
		catch (Exception e) {
			message = e.getMessage();
			goToRedirect = true;
		}
		
		if(!goToRedirect) {
			try {
				userService.registerUser(usernamer, pwdr, typer, mailr);
				message = "Successful registration";
			} 
			catch (Exception e) {
				message = e.getMessage();
			}
		}
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("errorMsgr", message);
		if(messagepwd != null) {
			ctx.setVariable("errorPswr", messagepwd);
		}
		templateEngine.process(path, ctx, response.getWriter());
	}

	public void destroy() {
		
	}
}
