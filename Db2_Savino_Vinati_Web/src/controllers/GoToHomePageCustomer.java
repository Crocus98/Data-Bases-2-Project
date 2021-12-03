package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import entities.User;

@WebServlet("/HomeCustomer")
public class GoToHomePageCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	public GoToHomePageCustomer() {
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
		
		String path = "/WEB-INF/HomeCustomer.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		//ctx.setVariable("preventiviCliente", preventiviCliente);
		//ctx.setVariable("products", prodottiDisponibili);
		//ctx.setVariable("errorMsg", "");
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		User user;
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		else {
			user = (User)session.getAttribute("user");
			if (!user.getTipo().equals("Cliente")) {
				response.sendRedirect(loginpath);
				return;
			}
		}
		Integer idprodotto;
		try {
			idprodotto = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("prodotto")));
			if(idprodotto <-1)
			{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Valore dei parametri scorretto.");
				return;
			}
		} catch (NullPointerException e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Valore dei parametri mancante.");
			return;
		}
		
		ProdottiDAO prodottiDAO = new ProdottiDAO(connection);
		String path;
		if (idprodotto == -1) {
			PreventiviDAO preventiviDAO = new PreventiviDAO(connection);
			List<Preventivo> preventiviCliente = new ArrayList<Preventivo>();
			List<Prodotto> prodottiDisponibili = new ArrayList<Prodotto>();
			try {
				preventiviCliente = preventiviDAO.findPreventiviByCliente(user.getId());
			} catch (SQLException e) {
				// for debugging only e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è possibile reperire i tuoi preventivi.");
				return;
			}
			try {
				prodottiDisponibili = prodottiDAO.getAllProductsFromDB();
			} catch (SQLException e) {
				// for debugging only e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Non è possibile reperire la lista dei prodotti.");
				return;
			}
			
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsg", "Error! Please select a Prodotto to continue.");
			ctx.setVariable("preventiviCliente", preventiviCliente);
			ctx.setVariable("products", prodottiDisponibili);
			path = "/WEB-INF/HomeCliente.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else {
			path = getServletContext().getContextPath() + "/CreatePreventivo?idprodotto=" +idprodotto;
			response.sendRedirect(path);
		}
		 */
	}


	public void destroy() {

	}

}
