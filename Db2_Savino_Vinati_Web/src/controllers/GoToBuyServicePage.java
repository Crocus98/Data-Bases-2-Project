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

import services.ServicePackageService;
import entities.Optionalproduct;
import entities.Service;
import entities.Servicepackage;

@WebServlet("/GoToBuyServicePage")
public class GoToBuyServicePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService spService;

	public GoToBuyServicePage() {
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

		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			 response.sendRedirect(loginpath);
			return;
		}

		// get and check params
		Integer servicepackageId = null;
		try {
			servicepackageId = Integer.parseInt(request.getParameter("servicepackageid"));
			System.out.print(servicepackageId);
		} catch (NumberFormatException | NullPointerException e) {
			// only for debugging e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
			return;
		}

		// If a service package with that ID exist
		// obtain the other details for it
	
		Servicepackage singlepackage = null;
		//List<Packageperiod> packageperiod = null;
		List<Service> services = null;
		List<Optionalproduct> optionalproduct = null;
		try {
			singlepackage = spService.findServicePackageById(servicepackageId);
			if (singlepackage == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Service Package not found");
				return;
			}
			
			//packageperiod = singlepackage.getPackageperiods();
			services = singlepackage.getServices();
			optionalproduct = singlepackage.getOptionalproducts();
		} catch (Exception e) {
			e.printStackTrace();
			 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to recover services and periods");
			return;
		}

		// Redirect to the Home page and add missions to the parameters
		String path = "/WEB-INF/BuyService.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("singlepackage", singlepackage);
		//ctx.setVariable("packageperiod", packageperiod);
		ctx.setVariable("services", services);
		ctx.setVariable("optionalproduct", optionalproduct);
		templateEngine.process(path, ctx, response.getWriter());
	}

	public void destroy() {
	}

}
