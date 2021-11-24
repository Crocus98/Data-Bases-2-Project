package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.TestService;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "services/TestService")
	private TestService testService;
       
    public Test() {
        super();
    }


    //To perform the test just /Test?test=value and it is successful if value is added in database
    //and you are returned to /index.html?test=success
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		testService.addToDb(request.getParameter("test"));
		String loginpath = getServletContext().getContextPath() + "/index.html?test=success";
		response.sendRedirect(loginpath);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
