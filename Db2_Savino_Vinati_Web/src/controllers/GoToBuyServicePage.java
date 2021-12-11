package controllers;

import java.io.IOException;
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
import services.ServicePackageService;
import entities.Servicepackage;
import entities.User;

@WebServlet("/GoToBuyServicePage")
public class GoToBuyServicePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/ServicePackageService")
	private ServicePackageService servicePackageService;

	public GoToBuyServicePage() {
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
		
		String message = null;
		boolean isBadRequest = false;
		Servicepackage servicepackage = null;
		try {
			Integer servicepackageId = Integer.parseInt(request.getParameter("servicepackageid"));
			servicepackage = servicePackageService.findServicePackageById(servicepackageId);
		}
		catch (NumberFormatException | NullPointerException e) {
			message = "Invalid service package id parameter";
			isBadRequest = true;
		}
		catch(Exception ee) {
			message = "Service package not found";
			isBadRequest = true;
		}
		
		String path = "/WEB-INF/BuyService.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if(isBadRequest) {
			ctx.setVariable("errorMsg", message);
		}
		else {
			ctx.setVariable("singlepackage", servicepackage);
		}
		templateEngine.process(path, ctx, response.getWriter());
	}

	public void destroy() {
		
	}

}
