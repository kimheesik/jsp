package com.study.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.web.entty.Notice;


@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
		String username = "sa";
		String password = "";
		
		try {
			Class.forName ("org.h2.Driver");
			Connection con = DriverManager.getConnection(jdbcURL, username, password);
		    String sql = "SELECT * FROM NOTICE WHERE ID=?";
		    PreparedStatement st = con.prepareStatement(sql);
		    st.setInt(1, id);
		    ResultSet rs = st.executeQuery();
		    rs.next();
		    
		    String title = rs.getString("TITLE");
		    String writerId = rs.getString("WRITER_ID");
		    String content = rs.getString("CONTENT");
		    Date regdate = rs.getDate("REGDATE");
		    int hit = rs.getInt("HIT");
		    String files = rs.getString("FILES");
		    
		    Notice notice = new Notice (id, title, writerId, content, regdate, hit, files);
		    
			request.setAttribute("n", notice);
			
		    rs.close();
		    st.close();                        		
		    con.close();                        		

		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//redirect
		

		//forward
		request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp")
		.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
