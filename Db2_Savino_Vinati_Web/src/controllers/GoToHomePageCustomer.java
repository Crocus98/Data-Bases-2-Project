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

import entities.Order;
import entities.Servicepackage;
import entities.User;
import services.OrderService;
import services.ServicePackageService;

@WebServlet("/HomeCustomer")
public class GoToHomePageCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;
	@EJB(name = "services/OrderService")
	private OrderService orderService;


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
		
		String loginpath = getServletContext().getContextPath() + "/GoToLoginPage";
		HttpSession session = request.getSession();
		User user = null;
		if (!(session.isNew() || session.getAttribute("user") == null)) {
			user = (User) session.getAttribute("user");
			if (user.getUsertype().getUsertype().equals("Employee")) {
				response.sendRedirect(loginpath);
				return;
			}
		}
		
		String message = null;
		boolean isBadRequest = false;
		List<Servicepackage> packages = null;
		List<Order> rejectedOrders = null;
		try {
			packages = servicePackageService.findAllServicePackages();
			if(user != null) {
				rejectedOrders = orderService.findAllRejectedOrders(user);
			}
			message = "";
		} 
		catch (Exception e) {
			isBadRequest = true;
			message = "Could not retrieve service packages to be bought";
			if(user != null) {
				message += " or rejected orders";
			}
		}
		
		String path = "/WEB-INF/HomeCustomer.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("errorMsg", message);
		if(!isBadRequest) {
			ctx.setVariable("servicepackages", packages);
			if(user != null) {
				ctx.setVariable("rejectedorders", rejectedOrders);
			}
		}
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


	public void destroy() {

	}

}
