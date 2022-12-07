package action;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardListAction");
		ActionForward forward = null;
		
		int listLimit = 10; // 한 페이지에 표시할 게시물 목록 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		int startRow = (pageNum - 1) * listLimit;

		String keyword = request.getParameter("keyword");

		if(keyword == null) {
			keyword = "";
		}
		
		// 게시물 목록 조회
		BoardListService service = new BoardListService();
		List<BoardBean> boardList = service.getBoardList(keyword, startRow, listLimit);
		
		// 게시물 목록 갯수 조회
		int listCount = service.getBoardListCount(keyword);
//		System.out.println(listCount);
		int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
		int maxPage = listCount / listLimit + (listCount % listLimit == 0 ? 0 : 1); 
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		int endPage = startPage + pageListLimit - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageInfo", pageInfo);
		
		forward = new ActionForward();
		forward.setPath("board/qna_board_list.jsp");
		forward.setRedirect(false);
		
		return forward; // BoardFrontController
	}

}
