package com.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		response.setContentType("text/html; charset=UTF-8");
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");		
		
		int v = 0;
		int result = 0;
		
		if (!v_.equals("")) v = Integer.parseInt(v_);
		
		if(op.equals("="))
		{
			int x = 0;
			int y = v;
			String operator ="";

//			x = (Integer)application.getAttribute("value");
//			String operator = (String)application.getAttribute("op");
			
//			x = (Integer)session.getAttribute("value");
//			String operator = (String)session.getAttribute("op");
			
			for(Cookie cookie:cookies) {
				if (cookie.getName().equals("value")) {
					x = Integer.parseInt(cookie.getValue());
					break;
				}
			}
			for(Cookie cookie:cookies) {
				if (cookie.getName().equals("op")) {
					operator = cookie.getValue();
					break;
				}
			}

			if(operator.equals("+"))
				 result = x + y;
			if(operator.equals("-"))
				 result = x - y;
			
			PrintWriter out = response.getWriter();
			out.printf("결과는 %d", result);			
		}
		else {
//			application.setAttribute("value", v);
//			application.setAttribute("op", op);			
//			session.setAttribute("value", v);
//			session.setAttribute("op", op);		
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", op);
			response.addCookie(valueCookie);
			response.addCookie(opCookie);
			response.sendRedirect("calc2.html");
		
		}
		
	}

}
