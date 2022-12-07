package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardDeleteProService {

	public boolean isBoardWriter(int board_num, String board_pass) {
		System.out.println("BoardDeleteService - isBoardWriter()");
		boolean isBoardWriter = false;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		isBoardWriter = dao.isBoardWriter(board_num, board_pass);
		
		JdbcUtil.close(con);
		
		return isBoardWriter; // BoardDeleteAction
	}

	public boolean removeBoard(int board_num) {
		System.out.println("BoardDeleteService - removeBoard()");
		boolean isDeleteSuccess = false;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		int deleteCount = dao.deleteBoard(board_num);
		
		if(deleteCount > 0) {
			JdbcUtil.commit(con);
			isDeleteSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isDeleteSuccess;
	}

}
