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

import exceptions.BadOrderParams;
import exceptions.BadServicePackage;
import entities.Optionalproduct;
import entities.Order;
import entities.Service;
import entities.Servicepackage;
import entities.User;
import entities.Validityperiod;
import services.OrderService;
import services.ServicePackageService;

 @WebServlet("/GoToConfirmPage")
 public class  GoToConfirmPage  extends HttpServlet {
 	private static final long serialVersionUID = 1L;
 	private TemplateEngine templateEngine;
 	@EJB(name = "services/OrderService")
 	private OrderService orderService;
        
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
 			
 			idservicepackage =  Integer.parseInt(request.getParameter("idsinglepackage"));
 			idvalidityperiod = Integer.parseInt(request.getParameter("idvalidity"));
 			String [] idoptionalproductstring = request.getParameterValues("idoptionalproducts");
 			String startdatestring = request.getParameter("startdate");
 			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
 			startdate = dateFormat.parse(startdatestring);
 			Date today = new Date();
 			if(idservicepackage == null || idservicepackage > 0
 					|| idvalidityperiod == null || idvalidityperiod > 0
 					|| startdatestring == null || startdatestring.isEmpty()
 					|| startdate.before(today)) {
 				throw new BadOrderParams("");
 			}
 			
 			if(idoptionalproductstring != null) {
 				idoptionalproducts = new ArrayList<>();
 				for (int i = 0; i < idoptionalproductstring.length; i++) {
 					idoptionalproducts.add(Integer.parseInt(idoptionalproductstring[i]));
 				}
 			}
 		
 		} 
 		catch (Exception e) {
 			message = "Invalid data inserted into the form.";
 			isBadRequest = true;
 		}
 		
 		//If data from the form are correct we try to create the order
 		if(!isBadRequest) {
 	 		try {
 	 			order = orderService.createOrderNoPersist(idservicepackage, idvalidityperiod, idoptionalproducts, startdate);
 	 		}
 	 		catch(Exception e) {
 	 			isBadRequest = true;
 	 			message = "Could not create order";
 	 		}
 		}
 		
 		//Moving to confirmation page
 		String path = "/WEB-INF/Confirmation.html";
 		ServletContext servletContext = getServletContext();
 		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
 		if(!isBadRequest) {
 			ctx.setVariable("order", order);
 		}
 		else {
 			ctx.setVariable("errorMsg", message);
 		}
 		templateEngine.process(path, ctx, response.getWriter());
 	}

 	public void destroy() {
 		
 	}
 }

