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
import jakarta.servlet.http.HttpSession;

@WebServlet("/DepositeServlet")
public class DepositeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DepositeServlet() {
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
		double amount = Double.parseDouble(request.getParameter("amount"));
		HttpSession session  = request.getSession();
		String email = (String)session.getAttribute("user");
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement ps= con.prepareStatement("update users set balance=balance+? where email=?");
			ps.setDouble(1, amount);
			ps.setString(2, email);
			int  i  = ps.executeUpdate();
			PrintWriter out= response.getWriter();
			if(i > 0) {
				out.println("Amount deposited");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
