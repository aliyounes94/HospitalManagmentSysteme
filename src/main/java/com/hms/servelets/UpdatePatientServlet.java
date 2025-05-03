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
 * Servlet implementation class UpdatePatientServlet
 */
@WebServlet("/UpdatePatientServlet")
public class UpdatePatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePatientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        
        String patientsPath = getServletContext().getRealPath("/WEB-INF/data/patients.json");
        
        try {
            List<Patient> patients = PatientDAO.readPatients(patientsPath);
            
            patients.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .ifPresent(patient -> {
                    patient.setName(name);
                    patient.setDob(dob);
                    patient.setAddress(address);
                    patient.setPhone(phone);
                });
            
            PatientDAO.writePatients(patientsPath, patients);
            response.sendRedirect("DoctorDashboard?success=Patient+updated");
            
        } catch (IOException e) {
            response.sendRedirect("DoctorDashboard?error=Error+saving+changes");
        }
    }
}
