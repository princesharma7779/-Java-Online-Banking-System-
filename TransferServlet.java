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

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public TransferServlet() {
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
		String reciever = request.getParameter("reciever");
		double amount = Double.parseDouble(request.getParameter("amount"));
		HttpSession session =request.getSession();
		String sender=(String)session.getAttribute("user");
		try {
			Connection con= DBConnection.getConnection();
			PreparedStatement debit= con.prepareStatement("updatee users set balance=balance=? where email=?");
			debit.setDouble(1, amount);
			debit.setString(2, sender);

			debit.executeUpdate();
			PreparedStatement credit=con.prepareStatement("update users set balance=balance+? where email=?");
			credit.setDouble(1, amount);
			credit.setString(2, reciever);

			credit.executeUpdate();
			PreparedStatement transaction= con.prepareStatement
					("insert intp transaction(sender, reiever,amount)values(?,?,?,?)");
			transaction.setString(1, sender);
			transaction.setString(2, reciever);
			transaction.setDouble(3, amount);

			transaction.executeUpdate();
			PrintWriter out = response.getWriter();
			out.println("Money Transferred");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
