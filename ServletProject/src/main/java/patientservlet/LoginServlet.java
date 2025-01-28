package patientservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")  
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaIsoft_DB", "root", "Password12@");
            String sql = "SELECT * FROM patients WHERE email = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                response.sendRedirect("Dashboard.html");
            } else {
                response.getWriter().println("Invalid email or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
