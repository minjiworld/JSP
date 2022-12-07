package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public class BoardListAction_backup {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
			forward = new ActionForward();
			forward.setPath("board/qna_board_list.jsp");
			forward.setRedirect(false);
		
		return forward; // BoardFrontController
	}
}
