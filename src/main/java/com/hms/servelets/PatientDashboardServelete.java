package com.hms.servelets;
import com.hms.dao.*;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.hms.model.*;
/**
 * Servlet implementation class PatientDashboardServelete
 */
@WebServlet("/PatientDashboardServelete")
public class PatientDashboardServelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientDashboardServelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String patientsPath = getServletContext().getRealPath("/WEB-INF/data/patients.json");

        List<Patient> patients = PatientDAO.readPatients(patientsPath);
       
        Patient patient = patients.stream()
                .filter(p -> p.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null);

        request.setAttribute("patient", patient);
        request.getRequestDispatcher("patientDashboard.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
