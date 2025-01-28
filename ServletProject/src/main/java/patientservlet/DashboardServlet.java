package patientservlet;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/DashboardServlet")  
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String patientId = (String) session.getAttribute("patientId");

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaIsoft_DB", "root", "Password12@");
                String sql = "SELECT * FROM patients WHERE patient_id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, patientId);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    
                    request.setAttribute("patientName", rs.getString("patient_name"));
                    request.setAttribute("gender", rs.getString("gender"));
                    request.setAttribute("phoneNumber", rs.getString("phone_number"));
                    request.setAttribute("country", rs.getString("country"));
                    request.setAttribute("state", rs.getString("state"));
                    request.setAttribute("address", rs.getString("address"));
                    request.setAttribute("pinCode", rs.getString("pin_code"));
                    request.setAttribute("email", rs.getString("email"));

                    RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
                    dispatcher.forward(request, response);  
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("LoginPage.html"); // Redirect to login if session is not available
        }
    }
}

