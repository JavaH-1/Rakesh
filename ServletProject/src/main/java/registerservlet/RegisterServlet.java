package registerservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@WebServlet("/RegisterServlet") 
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();

        try {

        	User user = mapper.readValue(request.getInputStream(), User.class);

            if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
                ObjectNode errorResponse = mapper.createObjectNode();
                errorResponse.put("status", "failure");
                errorResponse.put("message", "All fields are required.");
                out.print(errorResponse.toString());
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PatientRecords", "root", "Password12@");

            String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());

            ObjectNode jsonResponse = mapper.createObjectNode();
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Registration successful!");
            } else {
                jsonResponse.put("status", "failure");
                jsonResponse.put("message", "Registration failed. Please try again.");
            }

            out.print(jsonResponse.toString());
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            ObjectNode errorResponse = mapper.createObjectNode();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An error occurred: " + e.getMessage());
            out.print(errorResponse.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PatientRecords", "root", "Password12@");

            String query = "SELECT name, email, password FROM users";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            ArrayList<User> users = new ArrayList<>();

            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }

            String jsonResponse = mapper.writeValueAsString(users);
            out.print(jsonResponse);

            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();

            ObjectNode errorResponse = mapper.createObjectNode();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An error occurred: " + e.getMessage());
            out.print(errorResponse.toString());
        }
    }
}
