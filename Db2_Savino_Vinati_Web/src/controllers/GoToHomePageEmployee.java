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

import entities.Optionalproduct;
import entities.Service;
import entities.User;
import entities.Validityperiod;
import services.ServicePackageService;

@WebServlet("/HomeEmployee")
public class GoToHomePageEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;

	public GoToHomePageEmployee() {
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
		String loginpath = getServletContext().getContextPath() + "/GoToLoginPage";
		String message = null;
		boolean isBadRequest = false;
		HttpSession session = request.getSession();
		User user;
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		else {
			user = (User) session.getAttribute("user");
			if (!user.getUsertype().getUsertype().equals("Employee")) {
				response.sendRedirect(loginpath);
				return;
			}
		}
		
		List<Service> services = null;
		List<Optionalproduct> optionalproducts = null;
		List<Validityperiod>  validityperiods = null;
		
		try {
			services = servicePackageService.findAllServices();
			optionalproducts = servicePackageService.findAllOptionalproducts();
			validityperiods = servicePackageService.findAllValidityperiods();
			
			if(services == null || validityperiods == null
				|| services.isEmpty() || validityperiods.isEmpty()) {
				isBadRequest = true;
				message = "Some data necessary to create new servicepackages are missing";
			}
		}
		catch(Exception e) {
			isBadRequest = true;
			message = "Could not retrieve data to create new servicepackages correctly";
		}
		
		String path = "/WEB-INF/HomeEmployee.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if(isBadRequest) {
			ctx.setVariable("errorMsg2", message);
		}
		else{
			ctx.setVariable("services", services);
			ctx.setVariable("optionalproducts", optionalproducts);
			ctx.setVariable("validityperiods", validityperiods);
		}
		templateEngine.process(path, ctx, response.getWriter());
	}


	public void destroy() {
		
	}

}
