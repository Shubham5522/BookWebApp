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

@WebServlet("/editurl")
public class EditServlet extends HttpServlet 
{
	private static final String query = "UPDATE bookdata SET bookname=?,bookedition=?,bookprice=? WHERE id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//get the id record
		int id = Integer.parseInt(req.getParameter("id"));
		//get the edit data we want to edit
		String bookname = req.getParameter("bookname");
		String bookedition = req.getParameter("bookedition");
		float bookprice = Float.parseFloat(req.getParameter("bookprice"));

		
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
			ps.setString(1,bookname);
			ps.setString(2,bookedition);
			ps.setFloat(3, bookprice);
			ps.setInt(4, id);
			int count = ps.executeUpdate();
			if(count == 1)
			{
				pw.println("<script>");
				pw.println("alert('Record Updated successfully');");
				pw.println("window.location='booklist';"); // Change URL to your actual page
				pw.println("</script>");
			}
			else
			{
				pw.println("<script>");
				pw.println("alert('Record not Updates successfully');");
				pw.println("window.location='editScreen';"); // Change URL to your actual page
				pw.println("</script>");
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		} catch (Exception e) {
			e.printStackTrace();		
			pw.println("<h1>"+e.getMessage()+"</h1>"); 
			}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='booklist'>Book List</a>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		doGet(req,res);
	}
}
