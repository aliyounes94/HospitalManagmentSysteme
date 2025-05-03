package com.hms.servelets;
import com.hms.dao.PatientDAO;
import com.hms.dao.UserDAO;
import com.hms.model.*;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeletePatientServlet
 */
@WebServlet("/DeletePatientServlet")
public class DeletePatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePatientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("id");
        String usersPath = getServletContext().getRealPath("/WEB-INF/data/users.json");
        String patientsPath = getServletContext().getRealPath("/WEB-INF/data/patients.json");

        try {
            // Delete from users.json
            List<User> users = UserDAO.readUsers(usersPath);
            users.removeIf(user -> user.getUsername().equals(username));
            UserDAO.writeUsers(usersPath, users);

            // Delete from patients.json
            List<Patient> patients = PatientDAO.readPatients(patientsPath);
            patients.removeIf(patient -> patient.getUsername().equals(username));
            PatientDAO.writePatients(patientsPath, patients);

            response.sendRedirect("DoctorDashboard?success=Patient+deleted+successfully");

        } catch (IOException e) {
            response.sendRedirect("DoctorDashboard?error=Error+deleting+patient");
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
