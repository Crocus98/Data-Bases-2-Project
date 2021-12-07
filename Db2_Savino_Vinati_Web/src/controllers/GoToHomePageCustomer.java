package controllers;

import java.io.IOException;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Servicepackage;
import entities.User;
import services.ServicePackageService;

@WebServlet("/HomeCustomer")
public class GoToHomePageCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService spService;


	public GoToHomePageCustomer() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		User user;
		
		
		user = (User) session.getAttribute("user");
		if (!user.getUsertype().getUsertype().equals("Customer")) {
			response.sendRedirect(loginpath);
			return;
			}
		
		List<Servicepackage> packages=null;
		
		try {
			packages = spService.findAllServicePackages();
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to get data");
			return;
		}
			
		
		String path = "/WEB-INF/HomeCustomer.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		//ctx.setVariable("preventiviCliente", preventiviCliente);
		//ctx.setVariable("products", prodottiDisponibili);
		ctx.setVariable("servicepackage", packages);
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


	public void destroy() {

	}

}
