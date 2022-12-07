package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.BoardListAction;
import action.BoardWriteProAction;
import vo.ActionForward;

//@WebServlet("*.bo")
public class BoardFrontController_backup extends HttpServlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardFrontController");
		request.setCharacterEncoding("UTF-8");
//		String requestURL = request.getRequestURL().toString();
//		System.out.println("requestURL : " + requestURL);
//		
//		String requestURI = request.getRequestURI();
//		System.out.println("requestURI : " + requestURI);
//		
//		String contextPath = request.getContextPath(); // (/프로젝트명) 추출
//		System.out.println("contextPath : " + contextPath);
		
//		String command = request.getRequestURI().replace(contextPath, "");
//		String command = request.getRequestURI().substring(contextPath.length());
//		System.out.println("command : " + command);
		
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		if(command.equals("/BoardWriteForm.bo")) {
//			System.out.println("글쓰기폼!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("board/qna_board_write.jsp");
			dispatcher.forward(request, response);
//			String subject = request.getParameter("board_subject");
//			System.out.println(subject);
		} else if(command.equals("/BoardWritePro.bo")) {
			System.out.println("글쓰기 작업!");
			BoardWriteProAction action = new BoardWriteProAction();
			ActionForward forward = action.execute(request, response);
			
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		} else if(command.equals("/BoardList.bo")) {
			System.out.println("글목록!");
			BoardListAction action = new BoardListAction();
			ActionForward forward = action.execute(request, response);
			
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
