package action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardDetailAction");
		ActionForward forward = null;
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		BoardDetailService service = new BoardDetailService();
		BoardBean bean = service.getBoard(board_num, true);
//		System.out.println(bean);
		
		request.setAttribute("bean", bean);
		forward = new ActionForward();
		forward.setPath("board/qna_board_view.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
