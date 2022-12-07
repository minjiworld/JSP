package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardModifyFormAction");
		ActionForward forward = null;
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
//		System.out.println(board_num);
		
		BoardDetailService service = new BoardDetailService();
		BoardBean bean = service.getBoard(board_num, false);
		
		request.setAttribute("bean", bean);
		forward = new ActionForward();
		forward.setPath("board/qna_board_modify.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
