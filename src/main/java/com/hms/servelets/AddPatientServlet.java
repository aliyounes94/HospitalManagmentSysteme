package com.hms.servelets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hms.dao.PatientDAO;
import com.hms.dao.UserDAO;
import com.hms.model.Patient;
import com.hms.model.User;

/**
 * Servlet implementation class AddPatientServlet
 */
@WebServlet("/AddPatientServlet")
public class AddPatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPatientServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usersPath = getServletContext().getRealPath("/WEB-INF/data/users.json");
        String patientsPath = getServletContext().getRealPath("/WEB-INF/data/patients.json");

        // Create new User
        User newUser = new User(
            request.getParameter("username"),
            request.getParameter("password"),
            "patient"
        );

        // Create new Patient
        Patient newPatient = new Patient(
            request.getParameter("username"),
            request.getParameter("name"),
            request.getParameter("dob"),
            request.getParameter("address"),
            request.getParameter("phone")
        );

        // Update JSON files
        List<User> users = UserDAO.readUsers(usersPath);
        users.add(newUser);
        UserDAO.writeUsers(usersPath, users);

        List<Patient> patients = PatientDAO.readPatients(patientsPath);
        patients.add(newPatient);
        PatientDAO.writePatients(patientsPath, patients);

        response.sendRedirect("DoctorDashboardServlet");
    }

}
