package com.hms.servelets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hms.dao.PatientDAO;
import com.hms.model.Patient;

/**
 * Servlet implementation class EditPatientServlet
 */
@WebServlet("/EditPatientServlet")
public class EditPatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPatientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("id");
        String patientsPath = getServletContext().getRealPath("/WEB-INF/data/patients.json");
        
        try {
            List<Patient> patients = PatientDAO.readPatients(patientsPath);
            Patient patient = patients.stream()
                    .filter(p -> p.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (patient != null) {
                request.setAttribute("patient", patient);
                request.getRequestDispatcher("editPatient.jsp").forward(request, response);
            } else {
                response.sendRedirect("DoctorDashboardServlet?error=Patient+not+found");
            }
        } catch (IOException e) {
            response.sendRedirect("DoctorDashboardServlet?error=Error+loading+patient");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
