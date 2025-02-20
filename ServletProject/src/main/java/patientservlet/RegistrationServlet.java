package patientservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientName = request.getParameter("patientName");
        String patientId = request.getParameter("patientId");
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String country = request.getParameter("country");
        String state = request.getParameter("state");
        String address = request.getParameter("address");
        String pinCode = request.getParameter("pinCode");
        String emailId = request.getParameter("emailId");
        String password = request.getParameter("password");

        try {
            // JDBC code to insert data into database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaIsoft_DB", "root", "Password12@");
            String sql = "INSERT INTO patients (patient_name, patient_id, gender, phone_number, country, state, address, pin_code, email_id, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, patientName);
            stmt.setString(2, patientId);
            stmt.setString(3, gender);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, country);
            stmt.setString(6, state);
            stmt.setString(7, address);
            stmt.setString(8, pinCode);
            stmt.setString(9, emailId);
            stmt.setString(10, password);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("LoginPage.html"); // Redirect to login page after successful registration
            } else {
                response.getWriter().println("Registration failed. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
