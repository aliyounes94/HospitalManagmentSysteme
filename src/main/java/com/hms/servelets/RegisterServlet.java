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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        String usersPath = getServletContext().getRealPath("/WEB-INF/data/users.json");
        String patientsPath = getServletContext().getRealPath("/WEB-INF/data/patients.json");

        try {
            // Check if username exists
            List<User> users = UserDAO.readUsers(usersPath);
            if(users.stream().anyMatch(u -> u.getUsername().equals(username))) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Create new user
            User newUser = new User(username, password, "patient");
            users.add(newUser);
            UserDAO.writeUsers(usersPath, users);

            // Create new patient
            Patient newPatient = new Patient(username, name, dob, address, phone);
            List<Patient> patients = PatientDAO.readPatients(patientsPath);
            patients.add(newPatient);
            PatientDAO.writePatients(patientsPath, patients);

            response.sendRedirect("login.jsp?registered=true");

        } catch (IOException e) {
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

}
