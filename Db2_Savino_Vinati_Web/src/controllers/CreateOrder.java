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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Order;
import entities.Servicepackage;
import entities.User;
import exceptions.BadOrder;
import services.OrderService;
import services.ServicePackageService;


@WebServlet("/CreateOrder")
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
 	private TemplateEngine templateEngine;
 	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;
 	@EJB(name = "services/OrderService")
	private OrderService orderService;

    public CreateOrder() {
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
		List<Order> rejectedOrders = null;
		boolean isBadRequest = false;
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
		
		if(!isBadRequest) {
			Order order = (Order)request.getSession().getAttribute("order");
			try {
				String temp = StringEscapeUtils.escapeJava(request.getParameter("payment"));
				if(temp == "1") {
					order.setPaid(true);
				}
				else if (temp == "0") {
					order.setPaid(false);
				}
				else {
					throw new BadOrder("Payment parameter not found");
				}
			}
			catch(Exception e){
				isBadRequest = true;
				message = "Could not retrieve payment data. Your money are safe";
			}
			if(!isBadRequest) {
				try {
					order.setUser(user);
					orderService.createOrder(order);
					if(order.isPaid() == true) {
						message = "Order and Activation Schedule created successfully";
					}
					else {
						message = "Order created successfully but payment failed. Complete the payment as soon as possible";
					}
				}
				catch (Exception e)
				{
					isBadRequest = true;
					message = "Could not create order";
				}
			}
		}
		try {
			packages = servicePackageService.findAllServicePackages();
			rejectedOrders = orderService.findAllRejectedOrders(user);
		} catch (Exception e) {
			isBadRequest = true;
			message += ". Could not retrieve service packages to be bought or rejected orders.";
		}
		String path = "/WEB-INF/HomeCustomer.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if(isBadRequest) {
			ctx.setVariable("errorMsg", message);
		}
		else{
			ctx.setVariable("servicepackages", packages);
			ctx.setVariable("rejectedorders", rejectedOrders);
			ctx.setVariable("errorMsg", message);
		}
		request.getSession().removeAttribute("order");
		templateEngine.process(path, ctx, response.getWriter());
	}

}
