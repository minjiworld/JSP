package svc;

import java.sql.Connection;
import java.util.List;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardListService {

	public List<BoardBean> getBoardList(String keyword, int startRow, int listLimit) {
		System.out.println("BoardListService - getBoardList()");
		List<BoardBean> boardList = null;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		boardList = dao.selectBoardList(keyword, startRow, listLimit);
		
		JdbcUtil.close(con);
		
		return boardList; // BoardListAction
	}

	public int getBoardListCount(String keyword) {
		System.out.println("BoardListService - getBoardListCount()");
		int listCount = 0;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		listCount = dao.getBoardListCount(keyword);
		
		return listCount;
	}

}
