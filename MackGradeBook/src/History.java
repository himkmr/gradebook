

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class History
 */
@WebServlet("/History")
public class History extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String url = null;
	static Connection conn = null;
	Properties props = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public History() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		url = "jdbc:oracle:thin:testuser/password@localhost";
		
		// properties for creating connection to Oracle database
		props = new Properties();
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");

		
		// creating connection to Oracle database using JDBC
		try {
			conn = DriverManager.getConnection(url, props);
			if (conn == null)
				throw new SQLException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "Select * from student_records";
		
		PreparedStatement preStatement;
		ResultSet rst = null;
		String message ="<div align=\"center\"><table style=\"border:5px; solid black;\">";
		try {
			preStatement = conn.prepareStatement(query);
			rst = preStatement.executeQuery();
			
			while(rst.next())
			{
				String name = rst.getString("fname");
				name = name +" "+ rst.getString("lname");
				String grade = rst.getString("grades");
				
				message+="<tr><td style=\"padding:15px; border: 2px solid green; background-color: red; \">"
						+name +
				"</td><td style=\"padding:15px;border:2px solid green;background-color: orange; \">"+
				grade+"</td></tr>";
			}
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html");
		// Actual logic goes here.
		request.setAttribute("message", message);
		response.setContentType("text/html");
		getServletContext().getRequestDispatcher("/output.jsp").forward(
				request, response);
		
		
		
	}

}
