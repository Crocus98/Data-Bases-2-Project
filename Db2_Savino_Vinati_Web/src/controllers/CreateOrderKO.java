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
import services.ServicePackageService;


@WebServlet("/CreateOrderKO")
public class CreateOrderKO extends HttpServlet {
	private static final long serialVersionUID = 1L;
 	private TemplateEngine templateEngine;
 	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;

    public CreateOrderKO() {
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
 		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		List<Servicepackage> packages = null;
		boolean isBadRequest = false;
		boolean isBadRequest2 = false;
		String message = null;
		User user;
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		else {
			user = (User) session.getAttribute("user");
			if (!user.getUsertype().getUsertype().equals("Customer")) {
				response.sendRedirect(loginpath);
				return;
			}
		}
		if(session.getAttribute("order") == null) {
			isBadRequest = true;
			message = "Could not find order to be created";
		}
		if(isBadRequest) {
			try {
				packages = servicePackageService.findAllServicePackages();
				//Andranno messi qui anche tutti gli ordini forse (Se si vogliono nella home page)
			} catch (Exception e) {
				isBadRequest = true;
				message += ". Could not retrieve service packages to be bought."; //Da modificare se si mettono sopra gli ordini (Se si vogliono nella HomePage)
			}
		}
		else {
			Order order = (Order)request.getSession().getAttribute("order");
			try {
			//Code to create the order, update the user payment count and the Alert in a single transaction to avoid partial execution
			
			//
				message = "Order and Activation Schedule created successfully";
			}
			catch (Exception e)
			{
				isBadRequest2 = true;
				message = "Could not create order";
			}
		}
		String path = "/WEB-INF/HomeCustomer.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if(isBadRequest) {
			ctx.setVariable("errorMsg", message);
		}
		else{
			if(isBadRequest2) {
				ctx.setVariable("servicepackages", packages);
				ctx.setVariable("errorMsg", message);
			}
			else{
				ctx.setVariable("errorMsg", message);
			}
		}
		request.getSession().removeAttribute("order");
		templateEngine.process(path, ctx, response.getWriter());
		
	}

}
