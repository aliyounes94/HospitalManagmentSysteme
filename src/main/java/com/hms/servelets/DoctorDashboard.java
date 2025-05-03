package com.hms.servelets;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hms.dao.PatientDAO;
import com.hms.model.LabResult;
import com.hms.model.Patient;

/**
 * Servlet implementation class DoctorDashboard
 */
@WebServlet("/DoctorDashboard")
public class DoctorDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorDashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String patientsPath = getServletContext().getRealPath("/WEB-INF/data/patients.json");
		//  List<Patient> patients = PatientDAO.readPatients(patientsPath);
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read JSON file
        File jsonFile = new File(patientsPath);
        if (!jsonFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"File not found\"}");
            return;
        }

        // Parse JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonFile);
       
        if (jsonNode == null || !jsonNode.isArray() || jsonNode.size() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"No users found\"}");
            return;
        }
        List<Patient> patients = new ArrayList<>();
        // Print extracted array (for debugging)
        for (JsonNode user : jsonNode) {
        	patients.add(new Patient(user.has("username") ? user.get("username").asText() : "No email found"
        			, user.has("name") ? user.get("name").asText() : "No name found"
        			, user.has("dob") ? user.get("dob").asText() : "No dob found"
        			
        			,user.has("address") ? user.get("address").asText() : "No address found",
           user.has("phone") ? user.get("phone").asText() : "No phone found"));
            System.out.println("username: " + patients); // Print to console
            
        }
        // Send JSON response
      //  response.getWriter().write(patientList.toString());
       request.setAttribute("patients",patients);
       request.getRequestDispatcher("doctorDashboard.jsp").forward(request, response);	
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
