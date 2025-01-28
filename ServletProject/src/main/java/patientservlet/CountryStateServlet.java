package patientservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CountryStateServlet")
public class CountryStateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        
        if ("countries".equals(type)) {
            // Get countries from DB
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaIsoft_DB", "root", "Password12@");
                 Statement stmt = con.createStatement()) {

                String sql = "SELECT * FROM countries";
                ResultSet rs = stmt.executeQuery(sql);

                StringBuilder countries = new StringBuilder();
                while (rs.next()) {
                    countries.append(rs.getInt("id")).append(":").append(rs.getString("name")).append("\n");
                }
                response.setContentType("text/plain");
                response.getWriter().write(countries.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ("states".equals(type)) {
            String countryId = request.getParameter("countryId");

            // Get states based on the selected country
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaIsoft_DB", "root", "Password12@");
                 Statement stmt = con.createStatement()) {

                String sql = "SELECT * FROM states WHERE country_id = " + countryId;
                ResultSet rs = stmt.executeQuery(sql);

                StringBuilder states = new StringBuilder();
                while (rs.next()) {
                    states.append(rs.getInt("id")).append(":").append(rs.getString("name")).append("\n");
                }
                response.setContentType("text/plain");
                response.getWriter().write(states.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
