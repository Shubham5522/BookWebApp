package com.book.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet 
{
	private static final String query = "SELECT * FROM bookdata WHERE id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//get the id record
		int id = Integer.parseInt(req.getParameter("id"));
		//load jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//generate the connection
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///book","root","@Shubham23");
			PreparedStatement ps = con.prepareStatement(query);){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			pw.println("<!DOCTYPE html>");
			pw.println("<html lang='en'>");
			pw.println("<head>");
			pw.println("<meta charset='UTF-8'>");
			pw.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
			pw.println("<title>Book Registration</title>");
			pw.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<div class='container'>");
			pw.println("<div class='card mt-5'>");
			pw.println("<div class='card-head bg-danger text-white text-center p-3'><h2>Book Update</h2></div>");
			pw.println("<div class='card-body'>");

			pw.println("<form action='editurl?id="+id+"' method='post'>");

			pw.println("<div class='form-group'>");
			pw.println("<label for='bookname'>Book Name</label>");
			pw.println("<input type='text' class='form-control' id='bookname' name='bookname' value="+rs.getString(2)+">");
			pw.println("</div>");

			pw.println("<div class='form-group'>");
			pw.println("<label for='bookedition'>Book Edition</label>");
			pw.println("<input type='text' class='form-control' id='bookedition' name='bookedition' value="+rs.getString(3)+">");
			pw.println("</div>");

			pw.println("<div class='form-group'>");
			pw.println("<label for='bookprice'>Book Price</label>");
			pw.println("<input type='text' class='form-control' id='bookprice' name='bookprice' value="+rs.getFloat(4)+">");
			pw.println("</div>");

			pw.println("<button type='submit' name='update' class='btn btn-primary mt-2'>Update</button>");
			pw.println("</form>");

			pw.println("<a href='booklist' class='d-block mt-3'>Book List</a>");

			pw.println("</div>"); // Closing card-body
			pw.println("</div>"); // Closing card
			pw.println("</div>"); // Closing container
			
			pw.println("</body>");
			pw.println("</html>");
			 
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		} catch (Exception e) {
			e.printStackTrace();		
			pw.println("<h1>"+e.getMessage()+"</h1>"); 
			}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		doGet(req,res);
	}
}
