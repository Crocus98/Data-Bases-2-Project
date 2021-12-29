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

import entities.Optionalproduct;
import entities.Service;
import entities.User;
import entities.Validityperiod;
import services.OptionalProductService;
import services.ServicePackageService;


@WebServlet("/CreateOptionalProduct")
public class CreateOptionalProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/OptionalProductService")
	private OptionalProductService optionalProductService;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;
       
    public CreateOptionalProduct() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginpath = getServletContext().getContextPath() + "/GoToLoginPage";
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
		
		boolean isBadRequest = false;
		String productname = null;
		String message1 = null;
		String message2 = null;
		float monthlycost = 0;
		try {
			monthlycost = Float.parseFloat(request.getParameter("monthlycost"));
			productname = StringEscapeUtils.escapeJava(request.getParameter("productname"));
			isBadRequest = productname.isEmpty() || monthlycost <= 0;
		} 
		catch (NumberFormatException | NullPointerException e) {
			isBadRequest = true;
			message1 = "Invalid parameters for creating a new product.";
		}
		
		if(!isBadRequest) {
			try {
				optionalProductService.createOptionalProduct(productname, monthlycost);
				message1 = "Optional product created successfully";
			}
			catch(Exception e) {
				message1 = e.getMessage();
			}
		}
		
		List<Service> services = null;
		List<Optionalproduct> optionalproducts = null;
		List<Validityperiod>  validityperiods = null;
		if(!isBadRequest) {
			try {
				services = servicePackageService.findAllServices();
				optionalproducts = servicePackageService.findAllOptionalproducts();
				validityperiods = servicePackageService.findAllValidityperiods();
				
				if(services == null || optionalproducts == null || validityperiods == null
					|| services.isEmpty() || optionalproducts.isEmpty() || validityperiods.isEmpty()) {
					isBadRequest = true;
					message2 = "Some data necessary to create new servicepackages are missing";
				}
			}
			catch(Exception e) {
				isBadRequest = true;
				message2 = "Could not retrieve data to create new servicepackages correctly";
			}
		}
		
		String path = "/WEB-INF/HomeEmployee.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("errorMsg1", message1);
		if(isBadRequest) {
			ctx.setVariable("errorMsg2", message2);
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
