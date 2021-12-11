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

 import exceptions.BadServicePackage;
 import entities.Optionalproduct;
 import entities.Service;
import entities.Servicepackage;
import entities.User;
 import entities.Validityperiod;
 import services.ServicePackageService;

 @WebServlet("/GoToConfirmPage")
 public class  GoToConfirmPage  extends HttpServlet {
 	private static final long serialVersionUID = 1L;
 	private TemplateEngine templateEngine;
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
 		HttpSession session = request.getSession();
 		String loginpath = getServletContext().getContextPath() + "/index.html";
 		User user = null;
 		if (session.isNew() || session.getAttribute("user") == null) {
 			response.sendRedirect(loginpath);
 			return;
 		}
 		else {
 			user = (User)session.getAttribute("user");
 			if (!user.getUsertype().getUsertype().equals("Customer")) {
 				response.sendRedirect(loginpath);
 				return;
 			}
 		}
 		//Retrieving data from the service package form
 		boolean isBadRequest = false;
 		boolean isBadRequest2 = false;
 		String message = null;
 		String message2 = null;
 		String servicepackagename = null;
 	
 		// List<Integer> idoptionalproducts = new ArrayList<Integer>();
 		
 		// Single package product
 	
 		String idpackage = null;
 		// Services
 		List <Service> services = null;
 	
 		// Validity Period
 		String validityperiod = null;
 		String idvalidityperiod = null;
 		// Optional product
 		List <Optionalproduct> optionals = null;
 		List <Integer> idoptionals = new ArrayList<Integer>();
 		// Starting date of subscription
 		LocalDate startingdate = null;
 		
 		
 		
 		
 		try {
 			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
 			
 			String idpackagestring = StringEscapeUtils.escapeJava(request.getParameter("idsinglepackage"));
 			String idvalidityperiodstring = request.getParameter("idvalidity");
 		//	String[] idoptionalproductstring = request.getParameterValues("idoptionalproducts");
 			String startingdatestring = request.getParameter("optionalproductsvalue");
 			startingdate = LocalDate.parse(startingdatestring);
 			LocalDate today = java.time.LocalDate.now();
 	 		
 			
 			if(idpackagestring == null || idpackagestring.isEmpty()
 					|| idvalidityperiodstring == null || idvalidityperiodstring.isEmpty()
 					|| startingdatestring == null || startingdatestring.isEmpty() || startingdate.isBefore(today)) {
 				throw new BadServicePackage("");
 			}
 		} 
 		catch (Exception e) {
 			message = "Invalid data inserted into the form.";
 			isBadRequest = true;
 		}
 		
 	
 		
 		//Retrieve data about service package
 		Servicepackage servicepackage = null;
 		List<Service> serviceslist = null;
 		List<Optionalproduct> optionalproducts = null;
 		Map<Validityperiod, Float> validityperiods = null;
 		try {
 			int idpack =  Integer.parseInt(idpackage);
 			servicepackage = servicePackageService.findServicePackageById(idpack);
 			serviceslist = servicepackage.getServices();
 			optionalproducts = servicepackage.getOptionalproducts();
 			validityperiods = servicepackage.getValidityperiods();
 			
 			if(servicepackage == null || services == null || optionalproducts == null || validityperiods == null
 				|| services.isEmpty() || optionalproducts.isEmpty() || validityperiods.isEmpty()) {
 				isBadRequest2 = true;
 				message2 = ".\nSome data are missing";
 			}
 		}
 		catch(Exception e) {
 			isBadRequest2 = true;
 			message2 = ".\nCould not retrieve data correctly";
 		}
 		//Returning to home employee
 		String path = "/WEB-INF/Confirmation.html";
 		ServletContext servletContext = getServletContext();
 		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
 		if(!isBadRequest2) {
 			ctx.setVariable("servicepackage", servicepackage);
 			ctx.setVariable("services", serviceslist);
 			ctx.setVariable("optionalproducts", optionalproducts);
 			ctx.setVariable("validityperiods", validityperiods);
 			
 		}
 		else {
 			message = message + message2;
 		}
 		ctx.setVariable("errorMsg", message);
 		templateEngine.process(path, ctx, response.getWriter());
 	}

 	public void destroy() {
 		
 	}
 }

