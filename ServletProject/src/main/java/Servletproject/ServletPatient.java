package Servletproject;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletPatient")
public class ServletPatient extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql://127.0.0.1:3306/PatientRecords";
        String dbUsername = "root";
        String dbPassword = "Password12@";

        Class.forName(dbDriver);
        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");
        String patientName = request.getParameter("patientName");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");
        String email = request.getParameter("email");
        String admissionDate = request.getParameter("admissionDate");
        String dischargeDate = request.getParameter("dischargeDate");
        String doctorAssigned = request.getParameter("doctorAssigned");
        String roomNumber = request.getParameter("roomNumber");
        String insuranceProvider = request.getParameter("insuranceProvider");
        String policyNumber = request.getParameter("policyNumber");
        String emergencyContact = request.getParameter("emergencyContact");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Connection con = initializeDatabase();  
            PreparedStatement st = con.prepareStatement(
                "INSERT INTO patients (patient_id, patient_name, age, gender, address, contact_number, email, admission_date, discharge_date, doctor_assigned, room_number, insurance_provider, policy_number, emergency_contact) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, patientId);
            st.setString(2, patientName);
            st.setInt(3, Integer.parseInt(age));
            st.setString(4, gender);
            st.setString(5, address);
            st.setString(6, contactNumber);
            st.setString(7, email);
            st.setDate(8, Date.valueOf(admissionDate));
            st.setDate(9, Date.valueOf(dischargeDate));
            st.setString(10, doctorAssigned);
            st.setString(11, roomNumber);
            st.setString(12, insuranceProvider);
            st.setString(13, policyNumber);
            st.setString(14, emergencyContact);

            int rowsInserted = st.executeUpdate();  

            if (rowsInserted > 0) {
                out.write("{\"status\":\"success\",\"message\":\"Patient data inserted successfully!\"}");
            } else {
                out.write("{\"status\":\"error\",\"message\":\"Failed to insert patient data.\"}");
            }

            st.close();
            con.close();
        } catch (Exception e) {
            out.write("{\"status\":\"error\",\"message\":\"Error occurred: " + e.getMessage() + "\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String patientId = request.getParameter("searchPatientId");
        if (patientId == null || patientId.trim().isEmpty()) {
            out.write("{\"status\":\"error\",\"message\":\"Missing or invalid patientId parameter.\"}");
            return;
        }

        try {
            Connection con = initializeDatabase();
            String query = "SELECT * FROM patients WHERE patient_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, patientId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String jsonResponse = String.format(
                    "{" +
                    "\"status\":\"success\"," +
                    "\"patient\":{" +
                    "\"patientId\":\"%s\"," +
                    "\"patientName\":\"%s\"," +
                    "\"age\":%d," +
                    "\"gender\":\"%s\"," +
                    "\"address\":\"%s\"," +
                    "\"contactNumber\":\"%s\"," +
                    "\"email\":\"%s\"," +
                    "\"admissionDate\":\"%s\"," +
                    "\"dischargeDate\":\"%s\"," +
                    "\"doctorAssigned\":\"%s\"," +
                    "\"roomNumber\":\"%s\"," +
                    "\"insuranceProvider\":\"%s\"," +
                    "\"policyNumber\":\"%s\"," +
                    "\"emergencyContact\":\"%s\"" +
                    "}" +
                    "}",
                    rs.getString("patient_id"),
                    rs.getString("patient_name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    (rs.getDate("admission_date") != null) ? rs.getDate("admission_date").toString() : "N/A",
                    (rs.getDate("discharge_date") != null) ? rs.getDate("discharge_date").toString() : "N/A",
                    rs.getString("doctor_assigned"),
                    rs.getString("room_number"),
                    rs.getString("insurance_provider"),
                    rs.getString("policy_number"),
                    rs.getString("emergency_contact")
                );
                out.write(jsonResponse);
            } else {
                out.write("{\"status\":\"error\",\"message\":\"Patient not found.\"}");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            out.write("{\"status\":\"error\",\"message\":\"Error: " + e.getMessage() + "\"}");
        } finally {
            out.close();
        }
    }
}