package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import exceptions.BadOrder;
import exceptions.BadOrderParams;
import entities.Order;
import entities.Servicepackage;
import entities.User;
import services.OrderService;
import services.ServicePackageService;


 @WebServlet("/GoToConfirmPage")
 public class  GoToConfirmPage  extends HttpServlet {
 	private static final long serialVersionUID = 1L;
 	private TemplateEngine templateEngine;
 	@EJB(name = "services/OrderService")
 	private OrderService orderService;
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
		
		//Variable declaration
		String message = null;
		boolean isBadRequest = false;
		Order order = null;
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
 		String path;
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
	 		ctx.setVariable("order", order);
 		}
 		templateEngine.process(path, ctx, response.getWriter());
 	}

 	public void destroy() {
 		
 	}
 }

