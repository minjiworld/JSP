package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardModifyProService {
	
	public boolean isBoardWriter(BoardBean bean) {
		System.out.println("BoardDeleteService - isBoardWriter()");
		boolean isBoardWriter = false;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		isBoardWriter = dao.isBoardWriter(bean.getBoard_num(), bean.getBoard_pass());
		
		JdbcUtil.close(con);
		
		return isBoardWriter; // BoardModifyProAction
	}
	

	public boolean modifyBoard(BoardBean bean) {
		System.out.println("BoardModifyProService - updateBoard()");
		boolean isModifySuccess = false;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		int updateCount = dao.updateBoard(bean);
		if(updateCount > 0) {
			JdbcUtil.commit(con);
			isModifySuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isModifySuccess; // BoardModifyProAction
	}

}
