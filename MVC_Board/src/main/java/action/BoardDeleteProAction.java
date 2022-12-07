package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardDeleteProAction");
		ActionForward forward = null;
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String board_pass = request.getParameter("board_pass");
		
		try {
			BoardDeleteProService service = new BoardDeleteProService();
			boolean isBoardWriter = service.isBoardWriter(board_num, board_pass);
			
			
			if(!isBoardWriter) { // 삭제 권한 없음
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제 권한 없음!')");
				out.println("history.back()");
				out.println("</script>");
			} else { // 삭제 권한 있음
				BoardDetailService service2 = new BoardDetailService();
				BoardBean bean = service2.getBoard(board_num, false);
				
				boolean isDeleteSuccess = service.removeBoard(board_num);
				
				if(!isDeleteSuccess) { // 삭제 실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('삭제 실패!')");
					out.println("history.back()");
					out.println("</script>");
				} else { // 삭제 성공
					
					String uploadPath = "upload";
					String realPath = request.getServletContext().getRealPath(uploadPath);
					
					File f = new File(realPath, bean.getBoard_real_file());
					if(f.exists()) {
						f.delete();
					}
					forward = new ActionForward();
					forward.setPath("BoardList.bo?pageNum=" + request.getParameter("pageNum"));
					forward.setRedirect(true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return forward; // BoardFrontController
	}

}
