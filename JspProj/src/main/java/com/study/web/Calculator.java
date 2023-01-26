package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc")
public class Calculator extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String x_ = request.getParameter("x");
		String y_ = request.getParameter("y");
		String operator_ = request.getParameter("operator");		
		
		int x = 0;
		int y = 0;
		int result = 0;
		
		if (!x_.equals("")) x = Integer.parseInt(x_);
		if (!y_.equals("")) y = Integer.parseInt(y_);
				
		PrintWriter out = response.getWriter();
		if(operator_.equals("덧셈")) {
			 result = x + y;
		}
		if(operator_.equals("뺄셈")) {
			 result = x - y;
		}		
		out.printf("결과는 %d", result);
		
		
	}

}
