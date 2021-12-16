package controllers;

import java.io.IOException;
import java.util.ArrayList;
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

import exceptions.BadServicePackage;
import entities.Optionalproduct;
import entities.Service;
import entities.User;
import entities.Validityperiod;
import services.ServicePackageService;

@WebServlet("/CreateServicePackage")
public class CreateServicePackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;
       
    public CreateServicePackage() {
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
			if (!user.getUsertype().getUsertype().equals("Employee")) {
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
		List<Integer> idvalidityperiods = new ArrayList<Integer>();
		List<Integer> idservices = new ArrayList<Integer>();
		List<Float> monthlycosts = new ArrayList<Float>();
		List<Integer> idoptionalproducts = new ArrayList<Integer>();
		try {
			servicepackagename = StringEscapeUtils.escapeJava(request.getParameter("productname"));
			String[] monthlycoststring = request.getParameterValues("monthlycosts");
			String[] idvalidityperiodstring = request.getParameterValues("idvalidityperiods");
			String[] idallvalidityperiodstring = request.getParameterValues("idallvalidityperiods");
			String[] idservicestring = request.getParameterValues("idservices");
			String[] idoptionalproductstring = request.getParameterValues("idoptionalproducts");
			if(servicepackagename == null || servicepackagename.isEmpty() || monthlycoststring == null 
					|| idvalidityperiodstring == null || idservicestring == null || idallvalidityperiodstring == null
					|| idservicestring.length < 1 || monthlycoststring.length < 1 || idvalidityperiodstring.length < 1 
					|| idallvalidityperiodstring.length < 1 || monthlycoststring.length != idallvalidityperiodstring.length) {
				throw new BadServicePackage("");
			}
			for (int j = 0; j < idvalidityperiodstring.length; j++)
			{
				for(int i = 0; i < idallvalidityperiodstring.length; i++) {
					if(Integer.parseInt(idallvalidityperiodstring[i]) == Integer.parseInt(idvalidityperiodstring[j])) {
						if(monthlycoststring[i].isEmpty() || monthlycoststring[i] == null){
							throw new BadServicePackage("");
						}
						monthlycosts.add(Float.parseFloat(monthlycoststring[i]));
						idvalidityperiods.add(Integer.parseInt(idallvalidityperiodstring[i]));
						break;
					}
				}
			}
			for(int i = 0; i < idservicestring.length; i++) {
				idservices.add(Integer.parseInt(idservicestring[i]));
			}
			if(idoptionalproductstring != null) {
				for(int i = 0; i < idoptionalproductstring.length; i++) {
					idoptionalproducts.add(Integer.parseInt(idoptionalproductstring[i]));
				}
			}
		} 
		catch (Exception e) {
			message = "Invalid data inserted into the form. Impossible to create new service package";
			isBadRequest = true;
		}
		
		//Trying to create a new service package
		if(!isBadRequest) {
			try {
				servicePackageService.createNewServicePackage(servicepackagename, idservices, idoptionalproducts, idvalidityperiods, monthlycosts);
				message = "Service package created successfully";
			}
			catch(Exception e) {
				isBadRequest = true;
				message = e.getMessage();
			}
			
		}
		
		//Retrieve data necessary to return to home employee
		List<Service> services = null;
		List<Optionalproduct> optionalproducts = null;
		List<Validityperiod>  validityperiods = null;
		try {
			services = servicePackageService.findAllServices();
			optionalproducts = servicePackageService.findAllOptionalproducts();
			validityperiods = servicePackageService.findAllValidityperiods();
			
			if(services == null || optionalproducts == null || validityperiods == null
				|| services.isEmpty() || optionalproducts.isEmpty() || validityperiods.isEmpty()) {
				isBadRequest2 = true;
				message2 = ".\nSome data necessary to create new servicepackages are missing";
			}
		}
		catch(Exception e) {
			isBadRequest2 = true;
			message2 = ".\nCould not retrieve data to create new servicepackages correctly";
		}
		//Returning to home employee
		String path = "/WEB-INF/HomeEmployee.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if(!isBadRequest2) {
			ctx.setVariable("services", services);
			ctx.setVariable("optionalproducts", optionalproducts);
			ctx.setVariable("validityperiods", validityperiods);
		}
		else {
			message = message + message2;
		}
		ctx.setVariable("errorMsg2", message);
		templateEngine.process(path, ctx, response.getWriter());
	}

	public void destroy() {
		
	}
}
