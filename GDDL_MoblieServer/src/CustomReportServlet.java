

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CustomReportServlet")
public class CustomReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Custom_Module custom_module;
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		custom_module = new Custom_Module();
		custom_module.setC_number(Integer.parseInt(request.getParameter("@CustomNumber")));
		custom_module.setO_number(Integer.parseInt(request.getParameter("@OrderNumber")));
		custom_module.setCheck_content(request.getParameter("@CheckContent"));
		custom_module.setContent(request.getParameter("@Content"));
		custom_module.setReporter(request.getParameter("@Reporter"));
		custom_module.setDefandant(request.getParameter("@Defandant"));
		
		new CustomCareDAO().Report(custom_module);
		doGet(request, response);
	}

}
