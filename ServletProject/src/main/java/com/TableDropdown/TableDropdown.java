package com.TableDropdown;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TableDropdown")
public class TableDropdown extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/JavaIsoft_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Password12@";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String countryId = request.getParameter("countryId");

        if (countryId != null && !countryId.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT id, name FROM states WHERE country_id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(countryId));
                ResultSet rs = stmt.executeQuery();

                StringBuilder jsonOutput = new StringBuilder("[");
                while (rs.next()) {
                    if (jsonOutput.length() > 1) {
                        jsonOutput.append(",");
                    }
                    jsonOutput.append("{\"id\":").append(rs.getInt("id"))
                              .append(",\"name\":\"").append(rs.getString("name")).append("\"}");
                }
                jsonOutput.append("]");
                out.print(jsonOutput.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                out.print("[]");
            }
        } else {
            out.print("[]");
        }
    }
}
