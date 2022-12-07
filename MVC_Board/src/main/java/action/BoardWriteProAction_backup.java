package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public class BoardWriteProAction_backup {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		String subject = request.getParameter("board_subject");
		String content = request.getParameter("board_content");
		System.out.println("제목 : " + subject);
		System.out.println("내용 : " + content);
		
		boolean isWriteSuccess = true;
		if(!isWriteSuccess) {
			
		} else {
			forward = new ActionForward();
			forward.setPath("BoardList.bo");
			forward.setRedirect(true);
		}
		return forward; // BoardFrontController
	}
}
