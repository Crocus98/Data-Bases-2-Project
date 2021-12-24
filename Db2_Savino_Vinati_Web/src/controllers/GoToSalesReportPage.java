package controllers;

import java.io.IOException;
import java.util.Arrays;
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
import entities.MvAlert;
import entities.MvBestproduct;
import entities.MvInsolventUser;
import entities.MvPackage;
import entities.MvPackageperiod;
import entities.MvSuspendedorder;
import entities.User;
import services.SalesReportService;


@WebServlet("/GoToSalesReportPage")
public class GoToSalesReportPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/SalesReportService")
	private SalesReportService salesReportService;
       
    public GoToSalesReportPage() {
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
		String message = null;
		List<MvAlert> mvalerts = null;
		List<MvInsolventUser> mvinsolventusers = null;
		List<MvPackage> mvpackages = null;
		List<MvPackageperiod> mvpackageperiods = null;
		List<MvSuspendedorder> mvsuspendedorders = null;
		List<MvBestproduct> mvbestproducts = null;
		
		try {
		//	salesReportService.getSalesReportPageData(mvpackages, mvpackageperiods, mvinsolventusers, mvsuspendedorders, mvalerts, mvbestproducts);
			mvpackages = salesReportService.findAllmvpackages();
			mvpackageperiods = salesReportService.findAllmvpackageperiods();
			mvinsolventusers = salesReportService.findAllmvinsolventusers();
			mvsuspendedorders = salesReportService.findAllmvsuspendedorders();
			mvalerts = salesReportService.findAllmvalers();
			mvbestproducts = salesReportService.findAllbestproducts();
			message = "";
		}
		catch(Exception e) {
			isBadRequest = true;
			message = e.getMessage();
		}
		
		System.out.println("prova mvpackages: " + Arrays.toString(mvpackages.toArray()));
		
		
		String path = "/WEB-INF/SalesReport.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("errorMsg", message);
		if(!isBadRequest) {
			ctx.setVariable("mvalerts", mvalerts);
			ctx.setVariable("mvinsolventusers", mvinsolventusers);
			ctx.setVariable("mvpackages", mvpackages);
			ctx.setVariable("mvpackageperiods", mvpackageperiods);
			ctx.setVariable("mvsuspendedorders", mvsuspendedorders);
			ctx.setVariable("mvbestproduct", mvbestproducts);
		}
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	public void destroy() {
		
	}
}
