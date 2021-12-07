package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import entities.User;
import services.OptionalProductService;


@WebServlet("/CreateOptionalProduct")
public class CreateOptionalProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/OptionalProductService")
	private OptionalProductService optionalProductService;
       
    public CreateOptionalProduct() {
        super();
        // TODO Auto-generated constructor stub
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
		
		boolean isBadRequest = false;
		String productname = null;
		String message = null;
		float monthlycost = 0;
		try {
			monthlycost = Float.parseFloat(request.getParameter("monthlycost"));
			productname = StringEscapeUtils.escapeJava(request.getParameter("productname"));
			isBadRequest = productname.isEmpty() || monthlycost <= 0;
		} 
		catch (NumberFormatException | NullPointerException e) {
			isBadRequest = true;
			message = "Invalid parameters for creating a new product.";
		}
		
		if(!isBadRequest) {
			try {
				optionalProductService.createOptionalProduct(productname, monthlycost);
				message = "Optional product created successfully";
			}
			catch(Exception e) {
				message = e.getMessage();
			}
		}
		
		String path = "/WEB-INF/HomeEmloyee.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("errorMsg", message);
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	public void destroy() {
	}
}
