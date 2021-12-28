package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
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

import exceptions.BadOrder;
import exceptions.BadOrderParams;
import exceptions.CredentialsException;
import entities.Order;
import entities.Servicepackage;
import entities.User;
import services.OrderService;
import services.ServicePackageService;
import services.UserService;


 @WebServlet("/GoToConfirmPage")
 public class  GoToConfirmPage  extends HttpServlet {
 	private static final long serialVersionUID = 1L;
 	private TemplateEngine templateEngine;
 	@EJB(name = "services/OrderService")
 	private OrderService orderService;
 	@EJB(name = "services/UserService")
 	private UserService userService;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;
        
     public  GoToConfirmPage () {
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
		User user;
		if (!(session.isNew() || session.getAttribute("user") == null)) {
			user = (User) session.getAttribute("user");
			if (user.getUsertype().getUsertype().equals("Employee")) {
				response.sendRedirect(loginpath);
				return;
			}
		}
		
		String path;
		Order order = null;
		
		if(session.getAttribute("order")!= null) {
			//Obtain order
			order = (Order) session.getAttribute("order");
			//Add user to session
			String usrn = null;
			String pwd = null;
			
			try {
				usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
				pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
				
				if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
					throw new Exception("Missing or empty credential value");
				}
			}
			catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or wrong credential value");
				return;
			}

			try {
				user = userService.checkCredentials(usrn, pwd);
			}
			catch (CredentialsException | NonUniqueResultException e ) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
				return;
			}
			ServletContext servletContext = getServletContext();
	 		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
	 		if(user == null) {
				ctx.setVariable("errorMsg", "Incorrect username or password");
				path = "/index.html";
			}
			else {
				path = "/WEB-INF/Confirmation.html";
				request.getSession().setAttribute("user", user);
				ctx.setVariable("order", order);
			}
			templateEngine.process(path, ctx, response.getWriter());
			return;
		}
		
		//Variable declaration
		String message = null;
		boolean isBadRequest = false;
		Integer idservicepackage = null;
		Integer idvalidityperiod = null;
		Date startdate = null;
		List<Integer> idoptionalproducts = null;
		
		//Check data received from the form 
 		try {
 			idservicepackage =  Integer.parseInt(request.getParameter("idservicepackage"));
 			idvalidityperiod = Integer.parseInt(request.getParameter("idvalidityperiod"));
 			String [] idoptionalproductstring = request.getParameterValues("idoptionalproducts");
 			String startdatestring = request.getParameter("startdate");
 			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 			startdate = dateFormat.parse(startdatestring);


 			if(idservicepackage == null || idservicepackage < 1
 					|| idvalidityperiod == null || idvalidityperiod < 1
 					|| startdatestring == null || startdate.before(new Date())) {
 				throw new BadOrderParams("Invalid parameters.");
 			}
 			
 			if(idoptionalproductstring != null) {
 				idoptionalproducts = new ArrayList<>();
 				for (int i = 0; i < idoptionalproductstring.length; i++) {
 					idoptionalproducts.add(Integer.parseInt(idoptionalproductstring[i]));
 				}
 			}
	
 		} 
 		catch (Exception e) {
 			message = "ERROR: Invalid data inserted into the form. Could not create order";
 			isBadRequest = true;
 		}
 		
 		//If data from the form are correct we try to create the order
 		if(!isBadRequest) {
 	 		try {
 	 			order = orderService.createOrderNoPersist(idservicepackage, idvalidityperiod, idoptionalproducts, startdate);

 	 			if(order == null) {
 	 				throw new BadOrder("Order is null");
 	 			}

 	 		}
 	 		catch(Exception e) {
 	 			isBadRequest = true;
 	 			message = "ERROR: Could not create order. " + e.getMessage();
 	 		}
 		}
 		
 		
 		//Moving to confirmation page if successful or to service buy page if not
 		ServletContext servletContext = getServletContext();
 		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
 		if(isBadRequest){
 			String message2 = null;
 			boolean isBadRequest2 = false;
 			Servicepackage servicepackage = null;
 			try {
 				servicepackage = servicePackageService.findServicePackageById(idservicepackage);
 			}
 			catch (NumberFormatException | NullPointerException e) {
 				message2 = "Invalid service package id parameter.";
 				isBadRequest2 = true;
 			}
 			catch(Exception ee) {
 				message2 = "Service package not found";
 				isBadRequest2 = true;
 			}
 			path = "/WEB-INF/BuyService.html";
	 		
	 		if(isBadRequest2) {
	 			ctx.setVariable("errorMsg", message+". "+ message2);
	 		}
	 		else {
	 			ctx.setVariable("errorMsg", message);
		 		ctx.setVariable("singlepackage", servicepackage);
	 		}
 		}
 		else {
	 		path = "/WEB-INF/Confirmation.html";
	 		request.getSession().setAttribute("order", order);
	 		ctx.setVariable("order", order);
 		}
 		templateEngine.process(path, ctx, response.getWriter());
 	}
 	
 	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		HttpSession session = request.getSession();
 		String loginpath = getServletContext().getContextPath() + "/index.html";
 		Order order = null;
 		Integer orderid = null;
 		String message = null;
 		boolean isBadRequest = false;
 		List<Servicepackage> packages = null;
		List<Order> rejectedOrders = null;
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
 		try {
 			orderid = Integer.parseInt(request.getParameter("orderid"));
 			System.out.print("orderid: " + orderid);
 		}
 		catch(Exception e) {
 			message = "Invalid rejected order id";
 			isBadRequest = true;
 		}
 		if(!isBadRequest) {
	 		try {
	 			order = orderService.findOrderById(orderid);
	 		}
	 		catch(Exception e) {
	 			isBadRequest = true;
	 		}
	 		if(isBadRequest) {
	 			try {
	 				packages = servicePackageService.findAllServicePackages();
	 				rejectedOrders = orderService.findAllRejectedOrders(user);
	 				message = "";
	 			} 
	 			catch (Exception e) {
	 				isBadRequest = true;
	 				message = "Could not retrieve service packages to be bought or rejected orders";
	 			}
	 		}
 		}
 		
 		ServletContext servletContext = getServletContext();
 		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
 		String path = null;
 		if(!isBadRequest) {
 			path = "/WEB-INF/Confirmation.html";
 			request.getSession().setAttribute("order", order);
 			ctx.setVariable("order", order);
 		}
 		else {
 			path = "/WEB-INF/HomeCustomer.html";
 			ctx.setVariable("errorMsg", message);
 			ctx.setVariable("servicepackages", packages);
			ctx.setVariable("rejectedorders", rejectedOrders);
 		}
		templateEngine.process(path, ctx, response.getWriter());
	}

 	public void destroy() {
 		
 	}
 }

