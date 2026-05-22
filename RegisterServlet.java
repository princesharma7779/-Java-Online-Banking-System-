package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RegisterServlet() {
        // TODO Auto-generated constructor stub
    }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		String name = request.getParameter("name");
		String email= request.getParameter("email");
		String password = request.getParameter("password");
		double balance = Double.parseDouble(request.getParameter("balance"));
		try {
			Connection con =DBConnection.getConnection();
			PreparedStatement ps= con.prepareStatement
					("insert into users(name,email,password,balance)values(?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setDouble(4, balance);

			int  i =ps.executeUpdate();
			PrintWriter out  = response.getWriter();
			if(i>0) {
			out.println("Registration Successful");

			}else {
				out.println("Registration Failed");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}


	}

}
